if (window.console) {
    console.log("Welcome to your Play application's JavaScript!");
}

function loadPrice(doc) {
    jQuery.get("http://localhost:9000/rnd/rxbat", function (response) {
        doc.getElementById("price").value = parseFloat(response)
    }).fail(function (e) {
        alert('Woops! We was not able to call endpoint' + e.statusText);
    });

}