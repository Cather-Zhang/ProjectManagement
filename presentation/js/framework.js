var api_url = "https://28q0071kya.execute-api.us-east-2.amazonaws.com/beta";

/**
 * Creates a gray button, and sets its handler function
 * @param {string} text inner HTML of button
 * @param {string} handler function to call
 * @param {string} type
 * @returns 
*/
function buildButton(text, handler, type){
    if (type == undefined) {type = "secondary"}
    var b = document.createElement("button");
    b.setAttribute("type", "button");
    b.setAttribute("onclick", handler);
    b.classList = "btn btn-" + type;
    b.innerHTML = text;
    b.style.marginRight = ".3rem"
    return b;
}