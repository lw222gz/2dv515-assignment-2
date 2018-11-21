'use strict'

let result = document.getElementById("result");

document.getElementById("submit").addEventListener("click", function(){
    console.log("Event triggered");

    let amountOfClusters = document.getElementById("amountOfClusters").value;
    console.log(amountOfClusters);

    if(isNaN(amountOfClusters)){
        result.innerHTML="You must provide a number";
        return;
    }

    let http = new XMLHttpRequest();
    let url = "http://localhost:8080/kmeans/" + amountOfClusters;

    http.open("GET", url);
    http.send();

    http.onreadystatechange=function(){
        if(this.readyState===4 && this.status===200){
            //console.log(http.responseText);
            let resultTable = "<table> <tr><th>Centroid</th><th>Assigned blogs</th> </tr>"

            let requestResult = JSON.parse(http.responseText);
            console.log(requestResult);
            for(let i = 0; i < amountOfClusters; i++){
                resultTable += "<tr> <td>" + Object.entries(requestResult)[i][1].title +"</td> <td>";

                //console.log(Object.entries(requestResult)[i][1]);

                let blogs = Object.entries(requestResult)[i][1].blogs;
                for(let k = 0; k < blogs.length; k++){
                    resultTable += blogs[k] + "<br/>"
                }

                resultTable += "</td>";
            }

            resultTable +="</table>";
            result.innerHTML = resultTable;
        }
    }
    //document.getElementById("demo").innerHTML = "Hello World";
});