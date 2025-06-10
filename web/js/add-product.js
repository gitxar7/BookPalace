/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function changeImagePreview() {
    var image = document.getElementById("image-uploader");
    image.onchange = function () {

        var file = this.files[0];
        var url = window.URL.createObjectURL(file);
        document.getElementById("product-iamge-preview").src = url;
    }
}

async function loadFeatures(single) {
    const response = await fetch("LoadFeatures");
    if (response.ok) {
        const json = await response.json();

        const genreList = json.genreList;
        const ageList = json.ageList;
        const conditionList = json.conditionList;

        if (single) {
            loadSelectOptions("genre", genreList);
        } else {
            loadSelectOptions("genre", genreList);
            loadSelectOptions("age-rating", ageList);
            loadSelectOptions("condition", conditionList);
        }
    } else {
        document.getElementById("error-message").innerHTML = "Error: Please try again later!";
        document.getElementById("error-message").classList = "text-danger form-label fw-bold fs-5 bg-opacity-50 bg-black p-1 rounded-2";

    }

}

function loadSelectOptions(selectTagId, list) {
    const selectTag = document.getElementById(selectTagId);
    list.forEach(item => {
        let optionTag = document.createElement("option");
        optionTag.classList = "bg-black bg-opacity-50";
        optionTag.value = item.id;
        optionTag.innerHTML = item.name;
        selectTag.appendChild(optionTag);
    });
}

async function addProduct() {
    const genreSelectTag = document.getElementById("genre");
    const ageSelectTag = document.getElementById("age-rating");
    const conditionSelectTag = document.getElementById("condition");
    const titleTag = document.getElementById("title");
    const authorTag = document.getElementById("author");
    const isbnTag = document.getElementById("isbn");
    const priceTag = document.getElementById("price");
    const quantityTag = document.getElementById("quantity");
    const descriptionTag = document.getElementById("description");
    const imageTag = document.getElementById("image-uploader");

    const data = new FormData();
    data.append("genreId", genreSelectTag.value);
    data.append("ageId", ageSelectTag.value);
    data.append("conditionId", conditionSelectTag.value);
    data.append("title", titleTag.value);
    data.append("author", authorTag.value);
    data.append("isbn", isbnTag.value);
    data.append("price", priceTag.value);
    data.append("quantity", quantityTag.value);
    data.append("description", descriptionTag.value);
    data.append("image", imageTag.files[0]);

//    console.log(data);

    const response = await fetch("AddProduct", {
        method: "POST",
        body: data
    });

    if (response.ok) {
        const json = await response.json();
//        console.log(json);
        if (json.success) {
            alert(json.content);
            genreSelectTag.value = 0;
            ageSelectTag.value = 0;
            conditionSelectTag.value = 0;

            titleTag.value = "";
            authorTag.value = "";
            isbnTag.value = "";
            priceTag.value = "";
            quantityTag.value = 1;
            descriptionTag.value = "";

            imageTag.value = null;
            document.getElementById("product-iamge-preview").src = "images/upImage.png";
        } else {
            document.getElementById("error-message").innerHTML = json.content;
            document.getElementById("error-message").classList = "text-danger form-label fw-bold fs-5 bg-opacity-50 bg-black p-1 rounded-2";

        }

    } else {
        document.getElementById("error-message").innerHTML = "Error: Please try again later!";
        document.getElementById("error-message").classList = "text-danger form-label fw-bold fs-5 bg-opacity-50 bg-black p-1 rounded-2";

    }
}