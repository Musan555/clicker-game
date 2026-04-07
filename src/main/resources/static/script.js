let coins = 0;
const achievementAudio = new Audio("achievement.mp3");
const unlockedAchievements = new Set();

// --- Mostrar panel de logros ---
const achievementButton = document.getElementById("achievement-button");
const achievementPanel = document.getElementById("achievement-panel");
achievementButton.addEventListener("click", () => {
    achievementPanel.classList.toggle("hidden");
});

// --- Mostrar toast logros ---
function showAchievementToast(name, description) {
    const toast = document.createElement("div");
    toast.className = "achievement-toast";
    toast.innerHTML = `<b>${name}</b><br>${description}`;
    document.body.appendChild(toast);
    setTimeout(() => toast.classList.add("visible"), 100);
    setTimeout(() => {
        toast.classList.remove("visible");
        setTimeout(() => toast.remove(), 500);
    }, 3000);
}

// --- Formatear números ---
function formatNumber(value){
    if(value < 1000) return value.toString();
    const units = ["","K","M","B","T"];
    let i = Math.floor(Math.log10(value)/3);
    let scaled = value / Math.pow(1000,i);
    return scaled.toFixed(2)+units[i];
}

// --- Cargar estado del juego ---
async function loadGameState(){
    const response = await fetch("/api/game/state");
    const player = await response.json();

    coins = player.currentCoins;
    document.getElementById("coins").textContent = formatNumber(player.currentCoins);
    document.getElementById("prestigePoints").textContent = player.prestigePoints;
    document.getElementById("prestigeRequirement").textContent = formatNumber(player.prestigeRequirement);
    if(player.skillTree){
        document.getElementById("prestige-skill-points").textContent = player.skillTree.availablePrestigePoints;
    }

    // Barra prestige
    let percent = 0;
    if(player.prestigeRequirement > 0){
        percent = (player.coinsThisRun / player.prestigeRequirement) * 100;
    }
    percent = Math.min(percent,100);
    if(player.coinsThisRun === 0) percent = 0;
    document.getElementById("prestige-progress").style.width = percent+"%";

    const prestigeBtn = document.getElementById("prestige-button");
    if(player.coinsThisRun >= player.prestigeRequirement){
        prestigeBtn.classList.add("active");
    } else {
        prestigeBtn.classList.remove("active");
    }

    // --- Logros ---
    if(player.achievements){
        achievementPanel.innerHTML = ""; // limpiar panel

        player.achievements.forEach(a => {

            const id = a.type.id;
            const name = a.type.name;
            const description = a.type.description;

            // Toast
            if(a.unlocked && !unlockedAchievements.has(id)){
                unlockedAchievements.add(id);
                achievementAudio.currentTime = 0;
                achievementAudio.play();
                showAchievementToast(name, description);
            }

            // Panel logros
            const div = document.createElement("div");
            div.innerHTML = `
                <b>${name}</b><br>
                ${description}<br>
                Status: ${a.unlocked ? "✅" : "❌"}
                <hr>
            `;
            achievementPanel.appendChild(div);
        });
    }
}

// --- Click ---
document.getElementById("coin-button").addEventListener("click", async () => {
    const response = await fetch("/api/game/click",{method:"POST"});
    const player = await response.json();
    document.getElementById("coins").textContent = formatNumber(player.currentCoins);
});

// --- Prestige ---
async function prestige(){
    await fetch("/api/game/prestige",{method:"POST"});
    window.location.href = "skill-tree.html";
}
document.getElementById("prestige-button").addEventListener("click",prestige);

// --- Upgrades ---
async function loadUpgrades(){
    const response = await fetch("/api/upgrades");
    const upgrades = await response.json();
    const container = document.getElementById("upgrade-list");
    container.innerHTML="";
    upgrades.forEach((u,index)=>{
        const div = document.createElement("div");
        div.className="upgrade";
        div.innerHTML=`
            <b>${u.name}</b><br>
            Nivel: ${u.level}/${u.maxLevel}<br>
            Precio: ${formatNumber(u.cost)} $<br>
            <button onclick="buyUpgrade(${index})">Comprar</button>
        `;
        container.appendChild(div);
    });
}

async function buyUpgrade(index){
    await fetch(`/api/upgrades/buy/${index}`,{method:"POST"});
    await loadGameState();
    await loadUpgrades();
}

// --- Skills ---
async function loadSkills(){
    const container = document.getElementById("skill-tree");
    if(!container) return;
    const response = await fetch("/api/game/skills");
    const skills = await response.json();
    container.innerHTML="";
    skills.forEach((s,index)=>{
        const div = document.createElement("div");
        div.className="skill";
        div.innerHTML=`
            <b>${s.name}</b><br>
            ${s.description}<br>
            Nivel: ${s.level}/${s.maxLevel}<br>
            Coste: ${s.prestigeCost}<br>
            <button onclick="buySkill(${index})">Comprar</button>
        `;
        container.appendChild(div);
    });
}

async function buySkill(index){
    await fetch(`/api/game/skill/${index}`,{method:"POST"});
    await loadGameState();
    await loadSkills();
}

// --- Tabs ---
function showTab(tabId){
    document.querySelectorAll(".tab-content").forEach(tab=>tab.classList.remove("active"));
    document.querySelectorAll(".tab-button").forEach(btn=>btn.classList.remove("active"));
    document.getElementById(tabId).classList.add("active");
    const btn = Array.from(document.querySelectorAll(".tab-button"))
        .find(b=>b.getAttribute("onclick").includes(tabId));
    if(btn) btn.classList.add("active");
}

// --- Inicializar ---
loadGameState();
loadUpgrades();
loadSkills();
setInterval(loadGameState,1000);

