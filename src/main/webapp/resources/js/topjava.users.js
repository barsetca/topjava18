// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
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

    $(".custom-checkbox").each(function () {
        if ($(this).is(":checked")) {
            $(this).parent().parent().css("color", "green");
        } else {
            $(this).parent().parent().css("color", "red");
        }
    })
});

function changeEnable(checkBox) {
    const enabled = checkBox.is(":checked");
    $.ajax({
        url: context.ajaxUrl + checkBox.attr("id"),
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        $.get(context.ajaxUrl, function () {
            updateTable();
            successNoty("Change enable");
        })
    });
}
