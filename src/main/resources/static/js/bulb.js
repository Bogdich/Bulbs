'use strict';

const homeContainer = document.querySelector('#home-container');
const roomContainer = document.querySelector('#room-container');
const subscriptionContainer = document.querySelector('#subscription-container');
const subscriptionCard = document.querySelector('#subscription-card');
const disconnectButton = document.querySelector('#disconnect');
const sendButton = document.querySelector('#send-message');

const sockJsUrl = '/bulbs-message';
const changeStateUrl = '/bulbs-broker/';
const bulbsTopic = '/topic/';

let stompClient = null;
let subscription = null;

let subscribedBulbId = null;


let roomItemDom = function(id) {
    return "" +
        "<div id=\"room-" + id + "\" class=\"col col-4\" >" +
        "   <div class=\"card bg-info\">" +
        "       <img class=\"card-img-top\" src=\"svg/ic_lightbulb_outline_white_48px.svg\">" +
        "       <div class=\"card-body align-content-center\">" +
        "           <button id=\"" + id + "\" type=\"button\" class=\"btn btn-success\">Here we go!</button>" +
        "       </div>" +
        "   </div>" +
        "</div>"
};

let getRooms = function() {
    fetch('/bulbs')
        .then((resp) => resp.json())
        .then(function(roomList) {
            roomList.forEach(function (item) {
                roomContainer.insertAdjacentHTML('beforeend', roomItemDom(item.id));
                let button = document.getElementById(item.id);
                button.onclick = onSubscribe;
            })
        })
        .catch(function(error) {
            console.log(error);
            // If there is any error you will catch them here
        });
    connect();
};

function connect() {
    let socket = new SockJS('/message');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    console.log('Succeed')
}

function onError(error) {
    console.log(error);
}

function onSubscribe(event) {
    console.log('Subscription event: ');
    console.log(event);
    // Subscribe to the Public Topic
    subscribedBulbId = event.target.id;
    subscription = stompClient.subscribe('/app/topic/' + subscribedBulbId, onMessageReceived);
    homeContainer.hidden = true;
    subscriptionContainer.hidden = false;
    event.preventDefault();
}

function onMessageReceived(payload) {
    console.log('Received message: ');
    console.log(payload);
    let bulb = JSON.parse(payload.body);
    if (bulb.active === true) {
        subscriptionCard.classList.remove('bg-info');
        subscriptionCard.classList.add('br-warning');
    } else {
        subscriptionCard.classList.remove('br-warning');
        subscriptionCard.classList.add('bg-info');
    }
}

function sendMessage(event) {
    console.log('Send message event: ');
    console.log(event);
    let buttonId = subscribedBulbId;//event.explicitOriginalTarget.attributes;
    let newState = !(subscriptionCard.classList.contains('bg-warning'));
    if (buttonId && stompClient) {
        let chatMessage = {
            id: buttonId,
            active: newState
        };
        stompClient.send('/app/' + buttonId, {}, JSON.stringify(chatMessage));
    }
    event.preventDefault();
}

function disconnect(event) {
    console.log('Unsubscribe button event: ');
    console.log(event);
    subscription.unsubscribe();
    subscribedBulbId = null;
    homeContainer.hidden = false;
    subscriptionContainer.hidden = true;
    event.preventDefault();
}

document.addEventListener('DOMContentLoaded', getRooms);
sendButton.onclick = sendMessage;
disconnectButton.onclick = disconnect;
