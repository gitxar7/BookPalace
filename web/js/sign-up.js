/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

async function signUp() {

    const user_dto = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value,
    }

//    console.log(user_dto);
//    document.getElementById("error-message").innerHTML = user_dto;
//    document.getElementById("error-message").classList = "text-success";

    const response = await fetch(
            "SignUp", {
                method: "POST",
                body: JSON.stringify(user_dto),
                headers: {
                    "Content-Type": "application/json"
                }
            }
    );

    if (response.ok) {
        const json = await response.json();

        if (json.success) {
            alert(json.content);
            window.location = "verify-account.html";
        } else {
            document.getElementById("error-message").innerHTML = json.content;
        }
    } else {
        document.getElementById("error-message").innerHTML = "Please try again later!";
    }
}




