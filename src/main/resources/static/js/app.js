'use strict'

let result = document.getElementById("result");

document.getElementById("submit").addEventListener("click", function(){

    let amountOfClusters = document.getElementById("amountOfClusters").value;

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
            let resultTable = "<table> <tr><th>Centroid</th><th>Assigned blogs</th> </tr>"

            let requestResult = JSON.parse(http.responseText);
            
            for(let i = 0; i < amountOfClusters; i++){
                resultTable += "<tr> <td>" + Object.entries(requestResult)[i][1].title +"</td> <td>";

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
});