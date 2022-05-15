$(document).ready(function()
{
if ($("#alertSuccess").text().trim() == "")
{
$("#alertSuccess").hide();
}
$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
// Clear alerts---------------------
$("#alertSuccess").text("");
$("#alertSuccess").hide();
$("#alertError").text("");
$("#alertError").hide();
// Form validation-------------------
var status = validatePaymentForm();
if (status != true)
{
$("#alertError").text(status);
$("#alertError").show();
return;
}
// If valid------------------------
var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
$.ajax(
{
url : "PaymentsAPI",
type : type,
data : $("#formPayment").serialize(),
dataType : "text",
complete : function(response, status)
{
onPaymentSaveComplete(response.responseText, status);
}
});
});



function onPaymentSaveComplete(response, status)
{
if (status == "success")
{
var resultSet = JSON.parse(response);
if (resultSet.status.trim() == "success")
{
$("#alertSuccess").text("Successfully saved.");
$("#alertSuccess").show();
$("#divPaymentsGrid").html(resultSet.data);
} else if (resultSet.status.trim() == "error")
{
$("#alertError").text(resultSet.data);
$("#alertError").show();
}
} else if (status == "error")
{
$("#alertError").text("Error while saving.");
$("#alertError").show();
} else
{
$("#alertError").text("Unknown error while saving..");
$("#alertError").show();
}

$("#hidPaymentIDSave").val("");
$("#formPayment")[0].reset();
}



// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
 $("#hidPaymentIDSave").val($(this).closest("tr").find('#hidPaymentIDUpdate').val());
 $("#fullName").val($(this).closest("tr").find('td:eq(0)').text());
 $("#NIC").val($(this).closest("tr").find('td:eq(1)').text());
 $("#amount").val($(this).closest("tr").find('td:eq(2)').text());
 $("#date").val($(this).closest("tr").find('td:eq(3)').text());
 $("#bankName").val($(this).closest("tr").find('td:eq(4)').text());
 $("#debitCard").val($(this).closest("tr").find('td:eq(5)').text());
 $("#otpNumber").val($(this).closest("tr").find('td:eq(6)').text());
});

// DELETE==========================================
$(document).on("click", ".btnRemove", function(event)
{
$.ajax(
{
url : "PaymentsAPI",
type : "DELETE",
data : "payID=" + $(this).data("payid"),
dataType : "text",
complete : function(response, status)
{
onPaymentDeleteComplete(response.responseText, status);
}
});
});



function onPaymentDeleteComplete(response, status)
{
if (status == "success")
{
var resultSet = JSON.parse(response);
if (resultSet.status.trim() == "success")
{
$("#alertSuccess").text("Successfully deleted.");
$("#alertSuccess").show();
$("#divPaymentsGrid").html(resultSet.data);
} else if (resultSet.status.trim() == "error")
{
$("#alertError").text(resultSet.data);
$("#alertError").show();
}
} else if (status == "error")
{
$("#alertError").text("Error while deleting.");
$("#alertError").show();
} else
{
$("#alertError").text("Unknown error while deleting..");
$("#alertError").show();
}
}



// CLIENT-MODEL================================================================
function validatePaymentForm()
{
// fullName
if ($("#fullName").val().trim() == "")
{
return "Insert Customer Name.";
}
// NIC
if ($("#NIC").val().trim() == "")
{
return "Insert Customer NIC.";
}

// amount-------------------------------
if ($("#amount").val().trim() == "")
{
return "Insert Amount.";
}
// is numerical value
var tmpAmount = $("#amount").val().trim();
if (!$.isNumeric(tmpAmount))
{
return "Insert a numerical value for Amount.";
}
// Issued Date
if ($("#date").val().trim() == "")
{
return "Insert Issued Date.";
}
// bankName
if ($("#bankName").val().trim() == "")
{
return "Insert Bank Name.";
}
// debitCard
if ($("#debitCard").val().trim() == "")
{
return "Insert Debit Card Number.";
}
// otpNumber
if ($("#otpNumber").val().trim() == "")
{
return "Insert OTP Number.";
}
return true;
}