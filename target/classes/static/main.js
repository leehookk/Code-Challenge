document.addEventListener('DOMContentLoaded', function () {
    console.log("Page Loaded");
    
    populateTable();

    $("#insertForm").on("submit", function (event) {
        $.ajax({
            url: '/inventory/createNew',
            type: 'POST',
            data: $("#insertForm").serialize(),
            success: function (results) {
                console.log(results);
                $("#insResult").text(results);
                populateTable();
            }
        });
        return false;
    });


    $("#updateBut").on("click", function (event) {
        udata = {};
        udata["productName"]=$("#updateProductName").val();
        udata["quantity"]=$("#updateQuantity").val();
        // udata+="productName="+$("#updateProductName").val()+"&quantity="+$("#updateQuantity").val();
        // udata = JSON.stringify({ "productName": "on","quantity":"1" });
        console.log(udata);
        $.ajax({
            url: '/inventory/update/'+$("#updateProductId").val(),
            type: 'PUT',
            data: JSON.stringify(udata),
            contentType: 'application/json',
            success: function (results) {
                console.log(results);
                $("#updateResult").text(results);
                populateTable();
            },
            error: function (textStatus, errorThrown) {
                $("#updateResult").text(errorThrown);
            }
        });
        return false;
    });

    $("#deleteBut").on("click", function (event) {
        $.ajax({
            url: '/inventory/delete/'+$("#delProductId").val(),
            type: 'DELETE',
            success: function (results) {
                console.log(results);
                $("#delResult").text(results);
                populateTable();
            }
        });
        return false;
    });

});

function populateTable(){
    $("#myTable").find("tr:gt(0)").remove();
    $.get("/inventory/getAll", function (data, status) {
        // console.log(data);
        for(const d of data){
            // console.log(d);
            $('#myTable tr:last').after('<tr><td>'+d['productId']+'</td><td>'+d['productName']+'</td><td>'+d['quantity']+'</td></tr>');
        }
    });
}