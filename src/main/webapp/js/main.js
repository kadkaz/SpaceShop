var SimplePaginator = Backgrid.Extension.Paginator.extend({
    className: 'pagination center'
})


$(document).ready(function () {
    var AppRouter = Backbone.Router.extend({
        routes: {
            '*actions': 'defaultRoute'
        },
        defaultRoute: function (action) {
        }
    });

    new AppRouter()
    Backbone.history.start()


    new ShipGrid({ el: '#ships'})
})