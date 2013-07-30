_.extend(Backbone.Model.prototype, Backbone.Validation.mixin);

var SimplePaginator = Backgrid.Extension.Paginator.extend({
    className: 'pagination center'
})

/**
 * Show text field (enterEditMode) if the model field is not valid
 */
var ValidStringCell = Backgrid.StringCell.extend({

    render: function () {
        if (this.model.isValid(true)) {
            Backgrid.StringCell.prototype.render.call(this)
        } else {
            this.enterEditMode()
        }
        return this
    },
    exitEditMode: function () {
        if (this.model.isValid(true)) {
            Backgrid.StringCell.prototype.exitEditMode.call(this)
        }
        return this
    }
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