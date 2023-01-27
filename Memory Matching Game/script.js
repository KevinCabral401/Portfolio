/* 
BUGS:
If click same picture it thinks its a match

To Implement:
Keep track of score and attempts

wait one second after not match and flip back over.

Thoughts: 
keep running total of all 12 div covers and win once length == 0
*/


var imgArray = [];

imgArray.push('images/elmo.png');
imgArray.push('images/bert.png');
imgArray.push('images/ernie.png');
imgArray.push('images/cookie.png');
imgArray.push('images/oscar.png');
imgArray.push('images/bird.png');
imgArray.push('images/elmo.png');
imgArray.push('images/bert.png');
imgArray.push('images/ernie.png');
imgArray.push('images/cookie.png');
imgArray.push('images/oscar.png');
imgArray.push('images/bird.png');

var divHolder = [];
var itemsClicked = [];
var numMatches = 0;
var numMisses = 0;
var count = 0;

function toggle(item){
    if(count < 2){
        item.style = "background: none;"
        var image = item.previousElementSibling;
        divHolder.push(item);
        itemsClicked.push(image);
        count++;
        if(itemsClicked[0].src == itemsClicked[1].src && itemsClicked[0].id != itemsClicked[1].id){
            divHolder[0].remove();
            divHolder[1].remove();
            count = 0;
            numMatches++;
            itemsClicked = [];
            divHolder = [];
            document.getElementById("matches").innerHTML = numMatches;
            var items = document.getElementsByClassName("back");
            setTimeout(gameOver, 1000);
        } else if(itemsClicked[0].id == itemsClicked[1].id){
            itemsClicked.pop();
            divHolder.pop();
            count = 1;
        } else {
            numMisses++;
            itemsClicked = [];
            divHolder = [];
            count = 0;
            setTimeout(clear, 1000);
        }
    }
}

function gameOver(){
    var items = document.getElementsByClassName("back");
    if(items.length == 0){
        if(numMatches > numMisses){
            alert("You Won!");
        }
        else{
            alert("You Lost :(");
        }
    }
}

function clear(){
    var items = document.getElementsByClassName("back");
    document.getElementById("misses").innerHTML = numMisses;
    for(var i = 0; i < items.length; i++){
        items[i].style = "background: rgb(202, 14, 14);"
    }
}


function randomize(imgArray){
    for(var i = imgArray.length - 1; i > 0; i--){
        var randInd = Math.floor(Math.random() * (i + 1));
        var temp = imgArray[i];
        imgArray[i] = imgArray[randInd];
        imgArray[randInd] = temp;
    }
}

randomize(imgArray);

function populate(imgArray){

    var array = document.getElementsByClassName("images");
    console.log(array);
    for(var i = 0; i < array.length; i++){
        var img = array[i];
        var imgSrc = imgArray[i];
        img.setAttribute('src', imgSrc); 
    }
    console.log(array);
}

window.onload = populate(imgArray);
