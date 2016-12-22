
function ShowHideMenu(id,visible)
{
	var obj = document.getElementById(id);
	if(obj != null)
	{
		obj.style.visibility = visible;
		if(visible == "visible")
			obj.style.position = "relative";
		else 
			obj.style.position = "absolute";
			
	}
}

function ShowHideMenuByClick(id)
{
	var obj = document.getElementById(id);

	if(obj != null)
	{
		var visible = obj.style.visibility;
		if(visible == "visible")
		{
			obj.style.visibility = "hidden";
			obj.style.position = "absolute";
		}
		else 
		{
			obj.style.visibility = "visible";
			obj.style.position = "relative";

		}
	}
	
}
