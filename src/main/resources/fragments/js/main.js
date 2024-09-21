document.getElementById("limit").addEventListener("change", ev => {
    const limitValue = ev.target.value;
    window.location = '/home?limit=' + limitValue;
})

function add(element) {
    const newID = generateId();
    const tag = document.getElementById(element + `s-box`);
    const div = `<div class="input-group mb-3" id="${newID}">
                            <input type="text" class="form-control" name="${element + "s"}" placeholder="${element}">
                            <button class="btn btn-danger" type="button" onclick="deleteElement(${newID})"><i class="fa fa-trash"></i></button>
                        </div>`;
    tag.insertAdjacentHTML('beforeend', div)
}

function generateId() {
    return new Date().getTime();
}

function deleteElement(id) {
    document.getElementById(id).remove();
}