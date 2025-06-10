/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

async function verify() {

    const dto = {
        verification: document.getElementById("code").value
    };
    
//    console.log(dto);

    const response = await fetch(
            "Verification",
            {
                method: "POST",
                body: JSON.stringify(dto),
                headers: {
                    "Content-Type": "application/json"
                }
            }
    );

    if (response.ok) {
        const json = await response.json();

        if (json.success) {
            window.location = "index.html";
        } else {
            document.getElementById("error-message").innerHTML = json.content;
        }
    } else {
        document.getElementById("error-message").innerHTML = "Please try agin later";
    }
}

