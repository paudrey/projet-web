/**
 * Controls for important input like password or email adress
 */

function areDataCompliant(elem)
{
	if(isEmailCompliant(elem) && isPasswordCompliant(elem)){
		$("#inscriptionBtn").prop("disabled", false);
	}
	else{
		$("#inscriptionBtn").prop("disabled", true);
	}
};

function isEmailCompliant(elem)
{
	var secondInput = $("#emailToConfirm");
	var firstInput = $("#emailToConfirm").parent().parent().prev().find("input:text");
	
	if( (secondInput.val != "" && firstInput.val() != "") 
		|| (secondInput.val == "" && firstInput.val() != "")
		|| (secondInput.val != "" && firstInput.val() == ""))
	{
		if(secondInput.val() == firstInput.val()){
			secondInput.css("border", "rgb(154,178,39) solid 2px");
			firstInput.css("border", "rgb(154,178,39) solid 2px");
			return true;
		}
		else
		{
			secondInput.css("border", "rgb(204,40,24) solid 2px");
			firstInput.css("border", "rgb(204,40,24) solid 2px");
			return false;
		}
	}
	return false;
};

function isPasswordCompliant(elem)
{
	var secondInput = $("#pwdToConfirm");
	var firstInput = $("#pwdToConfirm").parent().parent().prev().find("input:text");
	
	if( (secondInput.val != "" && firstInput.val() != "") 
		|| (secondInput.val == "" && firstInput.val() != "")
		|| (secondInput.val != "" && firstInput.val() == ""))
	{
		if(secondInput.val() == firstInput.val()){
			secondInput.css("border", "rgb(154,178,39) solid 2px");
			firstInput.css("border", "rgb(154,178,39) solid 2px");
			return true;
		}
		else
		{
			secondInput.css("border", "rgb(204,40,24) solid 2px");
			firstInput.css("border", "rgb(204,40,24) solid 2px");
			return false;
		}
	}
	return false;
}

$(document).ready(function () {
	$("#inscriptionBtn").prop("disabled", true);
    $("#emailToConfirm").on("focusout", isEmailCompliant);
    $("#pwdToConfirm").on("focusout", isEmailCompliant);
});