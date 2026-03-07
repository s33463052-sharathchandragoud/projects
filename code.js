const MAX_CREDITS = 12;
let currentCredits = 0;

function showPortal(){

document.getElementById("loginPage").classList.add("hidden");
document.getElementById("mainPortal").classList.remove("hidden");

}

function registerCourse(courseCode,credits){

if(currentCredits + credits > MAX_CREDITS){

alert("Cannot exceed 12 credits!");
return;

}

const selectEl=document.getElementById(`select-${courseCode}`);
const actionEl=document.getElementById(`action-${courseCode}`);
const rowEl=document.getElementById(`row-${courseCode}`);

const seatCell=rowEl.children[2];

let seatText=seatCell.innerText.trim();

let parts=seatText.split("/");

let seats=parseInt(parts[0].trim());
let total=parseInt(parts[1].trim());

if(seats===0){

alert("No seats available!");
return;

}

seats--;

seatCell.innerText=`${seats} / ${total}`;

const selectedSection=selectEl.value;

currentCredits+=credits;

document.getElementById("creditCounter").innerText=
`${currentCredits} / ${MAX_CREDITS} Credits`;

selectEl.disabled=true;

rowEl.classList.add("bg-green-50");

actionEl.innerHTML=
`<span class="text-green-600 font-bold text-sm flex items-center justify-center">
<i class="fas fa-check-circle mr-1"></i> Registered
</span>`;

const scheduleList=document.getElementById("scheduleList");

if(currentCredits===credits)
scheduleList.innerHTML="";

const courseName=rowEl.querySelector(".text-xs").innerText;

const card=document.createElement("div");

card.className=
"p-3 bg-indigo-50 border border-indigo-100 rounded-lg flex justify-between items-center";

card.innerHTML=`
<div>
<div class="font-bold text-indigo-900">${courseCode}</div>
<div class="text-[10px] text-indigo-500 uppercase">${selectedSection} — ${courseName}</div>
</div>
<i class="fas fa-check text-indigo-400 text-xs"></i>
`;

scheduleList.appendChild(card);

}