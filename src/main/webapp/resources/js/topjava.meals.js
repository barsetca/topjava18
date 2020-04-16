function filter() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        // url: context.ajaxUrl + "filer",
        data: $("#filter").serialize()
    }).done(updateTableFromFilter);
    // done(updateTable);

}

$(function () {
    makeEditable({
        updateTable: filter,
        ajaxUrl: "ajax/profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })

    });
});

