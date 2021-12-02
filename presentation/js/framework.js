function buildButton(text, handler){
    var b = document.createElement("button");
    b.setAttribute("type", "button");
    b.setAttribute("onclick", handler);
    b.classList = "btn btn-secondary"
    b.innerHTML = text;
    b.style.marginRight = ".3rem"
    return b;
}
