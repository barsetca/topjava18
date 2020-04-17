// $(document).ready(function () {

var ajaxUrl = "ajax/admin/users/";
$(function () {
    makeEditable({
            updateTable: function () {
                $.get(ajaxUrl, updateTableFromFilter);
            },
            ajaxUrl: ajaxUrl,
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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
                        "asc"
                    ]
                ]
            })
        }
    );

    // $(".custom-checkbox").each(function () {
    //     if ($(this).is(":checked")) {
    //         $(this).parent().parent().css("color", "green");
    //     } else {
    //         $(this).parent().parent().css("color", "red");
    //     }
    // })
});

function changeEnable(checkBox) {
    const enabled = checkBox.is(":checked");
    $.ajax({
        url: ajaxUrl + checkBox.attr("id"),
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        successNoty(enabled ? "Enabled" : "Disabled");
       // context.updateTable();
    });
}
