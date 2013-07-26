var Ship = Backbone.Model.extend({

})

var Ships = Backbone.PageableCollection.extend({
    state: {
        firstPage: 0,
        perPage: 'amountPerPage',
        pageSize: 10,
        currentPage: 0
    },
    model: Ship,
    url: "/api/ship/list",
    fetch: function (options) {
        var self = this
        $.ajax('/api/ship/list/count', { async: false, success: function (data) {
            self.state.totalRecords = parseInt(data)
        }})

        return Backbone.PageableCollection.prototype.fetch.call(this, options)
    }
})

var ShipGrid = Backgrid.Grid.extend({
    className: 'table table-hover table-striped',
    initialize: function (options) {
        var ships = new Ships()

        options.columns = [{
            name: "name", label: 'Name', editable: false,
            cell: Backgrid.StringCell,
            headerCell: Backgrid.HeaderCell
        }]

        options.domain = Ship
        options.collection = ships

        this.constructor.__super__.initialize.apply(this, [options])

        var paginator = new SimplePaginator({collection: ships})

        this.render().$el.after(paginator.render().$el)

        //this.listenTo(ships, "backgrid:edited", this.edited);
        ships.getFirstPage()
    }
})