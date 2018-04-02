'use strict';

require(['knockout-3.4.2', 'jquery-3.2.1.min', 'popper.min', 'bootstrap.min'], function(ko) {

    function BulbsViewModel() {

        //data
        var self = this;
        self.bulbsList = ko.observableArray([]);

        //behavior
        self.getRooms = function () {
            self.bulbsList([]);
            var request = $.ajax({
                url: 'bulbs',
                type: 'GET',
                dataType: 'json'
            });
            request
                .done(function (bulbs) {
                    self.bulbsList(bulbs);
                })
                .error(function () {

                });
        };

        //action
        self.getRooms();
    }

    ko.applyBindings(new BulbsViewModel());
});
