var Ship = Backbone.Model.extend({
    initialize: function () {
        // stupid bug in backbone 1.0.0 and backbone-pageable
        // https://github.com/wyuenho/backbone-pageable/issues/70
        this.url = function () {
            return '/api/ship/' + this.get('id')
        }
    }
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

var MenuCell = Backgrid.Cell.extend({
    className: "menu",
    events: {
        "click a .icon-trash": "trash"
    },
    render: function () {
        this.$el.empty();
        this.$el.html($('<a><i class="icon-trash"></i></a>').click(this.trash));
        this.delegateEvents();
        return this
    },
    trash: function () {
        this.model.destroy({ success: function () {
                //notifyDialog.info($.i18n('info.apply.successfully'))
            }
        })
    }
})

var ShipGrid = Backgrid.Grid.extend({
    initialize: function (options) {
        var ships = new Ships()

        options.columns = [
            {
                name: "", label: 'Menu', editable: false,
                cell: MenuCell
            },
            {
                name: "name", label: 'Name',
                cell: Backgrid.StringCell
            }
        ]

        options.collection = ships

        this.constructor.__super__.initialize.apply(this, [options])

        var paginator = new SimplePaginator({collection: ships})
        this.render().$el.after(paginator.render().$el)

        this.listenTo(ships, "backgrid:edited", this.edited);

        ships.getFirstPage()
    },
    edited: function (model) {
        //if (!model.hasChanged() || model.isNew() || !model.isValid(true) || (model.options && model.options.save === false)) return;
        // bug in backbone-pageable
        console.log(model.url)
        model.save({}, {success: function () {

        }});
    }
})