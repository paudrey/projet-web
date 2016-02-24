/**
 * Controls for important input like password or email adress
 */

function areDataCompliant(elem)
{
	if(testEmailCompliance(elem) && testPasswordCompliance(elem)){
		$("#inscriptionBtn").prop("disabled", false);
	}
	else{
		$("#inscriptionBtn").prop("disabled", true);
	}
};

function isPasswordCompliant(elem)
{
	if(testPasswordCompliance(elem))
		$("#inscriptionBtn").prop("disabled", false);
	else
		$("#inscriptionBtn").prop("disabled", true);
}

function testEmailCompliance(elem)
{
	var secondInput = $("#emailToConfirm");
	var firstInput = $("#emailToConfirm").parent().parent().prev().find("input:text");
	
	if(secondInput.val() == firstInput.val() && secondInput.val() != "" && firstInput.val() != "")
	{
		secondInput.css("border", "rgb(154,178,39) solid 2px");
		firstInput.css("border", "rgb(154,178,39) solid 2px");
		return true;
	}
	else if(secondInput.val() == "" && firstInput.val() == ""){
		secondInput.css("border", "rgb(41,143,244) solid 2px");
		firstInput.css("border", "rgb(41,143,244) solid 2px");
		return false;
	}
	else
	{
		secondInput.css("border", "rgb(204,40,24) solid 2px");
		firstInput.css("border", "rgb(204,40,24) solid 2px");
		return false;
	}
	return false;
};

function testPasswordCompliance(elem)
{
	var secondInput = $("#pwdToConfirm");
	var firstInput = $("#pwdToConfirm").parent().parent().prev().find("input:password")
	

		if( (secondInput.val() == firstInput.val()) && (secondInput.val() != null && secondInput.val() != "") && (firstInput.val() != null && firstInput.val() != ""))
		{
			secondInput.css("border", "rgb(154,178,39) solid 2px");
			firstInput.css("border", "rgb(154,178,39) solid 2px");
			return true;
		}
		else if((secondInput.val() == "" || secondInput.val() == null) && (firstInput.val() == "" || firstInput.val() == null))
		{
			secondInput.css("border", "rgb(41,143,244) solid 2px");
			firstInput.css("border", "rgb(41,143,244) solid 2px");
			return false;
		}
		else
		{
			secondInput.css("border", "rgb(204,40,24) solid 2px");
			firstInput.css("border", "rgb(204,40,24) solid 2px");
			return false;
		}
	return false;
}

$(document).ready(function () {
	$("#inscriptionBtn").prop("disabled", true);
	
	if( ($("#emailToConfirm").val() != null) && ($("#pwdToConfirm").val() != null))
	{
		$("#emailToConfirm").on("input", areDataCompliant);
		$("#pwdToConfirm").on("input", areDataCompliant);
	}		
	if(($("#emailToConfirm").val() == null) && ($("#pwdToConfirm").val() != null))
		$("#pwdToConfirm").on("input", isPasswordCompliant);
});