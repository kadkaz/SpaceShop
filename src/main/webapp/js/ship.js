var Ship = Backbone.Model.extend({
    validation: { name: { required: true } },
    initialize: function () {
        // stupid bug in backbone 1.0.0 and backbone-pageable
        // https://github.com/wyuenho/backbone-pageable/issues/70
        this.url = function () {
            return '/api/ship/' + (this.get('id') ? this.get('id') : 'new')
        }
    }
})

var Ships = Backbone.PageableCollection.extend({
    state: {
        firstPage: 0,
        perPage: 'amountPerPage',
        pageSize: 10,
        currentPage: 0,
        sortKey: "name",
        order: -1
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

var MenuCell = Backgrid.Cell.extend({
    trashButton: '<a><i class="icon-trash"></i></a>',
    className: "menu",
    events: {
        "click a .icon-trash": "trash"
    },
    render: function () {
        this.$el.empty();
        this.$el.html($(this.trashButton));
        this.delegateEvents();
        return this
    },
    trash: function () {
        this.model.destroy({ success: function () {
                $(".notify").text("Successfully deleted").show().fadeOut(1000)
            }
        })
    }
})

var ShipGrid = Backgrid.Grid.extend({
    addButton: '<button type="button" class="btn btn-default">Add</button>',
    initialize: function (options) {
        var ships = new Ships()

        options.columns = [
            {
                sortable: false,
                name: "", label: '', editable: false,
                cell: MenuCell
            },
            {
                className: 'name',
                name: "name", label: 'Name',
                cell: ValidStringCell
            }
        ]

        options.collection = ships

        this.constructor.__super__.initialize.apply(this, [options])

        var paginator = new SimplePaginator({collection: ships})
        this.$el.after(paginator.render().$el).after($(this.addButton).bind('click', this, this.addShip))

        this.render()

        this.listenTo(ships, "backgrid:edited", this.edited);

        ships.getFirstPage()
    },
    edited: function (model) {
        model.save({}, {success: function () {
            $(".notify").text("Successfully updated").show().fadeOut(1000)
        }});
    },
    addShip: function (e) {
        var grid = e.data
        var c = grid.options.collection;
        var last = c.at(c.length - 1)
        // if we do not have any new row
        if (!last || !last.isNew()) {
            var newShip = new Ship()
            grid.insertRow(newShip)
            grid.focusCell(grid, newShip)
        } else {
            grid.focusCell(grid, last)
        }
        return false
    },
    getRowAtModel: function (model) {
        var index = this.options.collection.indexOf(model);
        return this.body.rows[index]
    },
    focusCell: function (grid, model) {
        var row = this.getRowAtModel(model)
        for (var i = 0; i < row.cells.length; i++) {
            if (row.cells[i].currentEditor) {
                row.cells[i].currentEditor.$el.focus()
                break
            }
        }
    }
})