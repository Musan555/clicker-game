let coins = 0;
const achievementAudio = new Audio("achievement.mp3");

// 🔥 DESBLOQUEO DE AUDIO (Chrome)
let audioUnlocked = false;
document.addEventListener("click", () => {
    if (!audioUnlocked) {
        achievementAudio.play().then(() => {
            achievementAudio.pause();
            achievementAudio.currentTime = 0;
            audioUnlocked = true;
        }).catch(() => {});
    }
}, { once: true });

// 🔥 persistencia
const unlockedAchievements = new Set(
    JSON.parse(localStorage.getItem("unlockedAchievements")) || []
);

// 🔥 CONTROL DE LOGROS (NUEVO)
let achievementsReady = false;

// 🔥 reset logros
function resetAchievements(){
    localStorage.removeItem("unlockedAchievements");
    unlockedAchievements.clear();
    achievementsReady = false;
}

// --- Mostrar panel de logros ---
const achievementButton = document.getElementById("achievement-button");
const achievementPanel = document.getElementById("achievement-panel");

achievementButton.addEventListener("click", () => {
    achievementPanel.classList.toggle("hidden");
});

// --- ⚙️ AJUSTES ---
const settingsButton = document.getElementById("settings-button");
const settingsPanel = document.getElementById("settings-panel");

if (settingsButton && settingsPanel) {
    settingsButton.addEventListener("click", () => {
        settingsPanel.classList.toggle("hidden");
    });
}

// --- toast ---
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

// --- formato números ---
function formatNumber(value){
    if(value < 1000) return value.toString();
    const units = ["","K","M","B","T"];
    let i = Math.floor(Math.log10(value)/3);
    let scaled = value / Math.pow(1000,i);
    return scaled.toFixed(2)+units[i];
}

// --- EXPORT ---
async function exportSave(){
    const res = await fetch("/api/game/save/export");
    const data = await res.json();

    const blob = new Blob([JSON.stringify(data)], {type: "application/json"});
    const url = URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.href = url;

    // 🔥 nombre con fecha
    const now = new Date();
    const fecha =
        now.getFullYear() + "-" +
        String(now.getMonth() + 1).padStart(2, '0') + "-" +
        String(now.getDate()).padStart(2, '0') + "_" +
        String(now.getHours()).padStart(2, '0') + "-" +
        String(now.getMinutes()).padStart(2, '0');

    a.download = `save_${fecha}.json`;

    a.click();
}

// --- 📂 IMPORT SAVE (NUEVO) ---
function importSave(){
    const input = document.createElement("input");
    input.type = "file";
    input.accept = ".json";

    input.addEventListener("change", async (e) => {
        const file = e.target.files[0];
        if (!file) return;

        try {
            const text = await file.text();
            const data = JSON.parse(text);

            await fetch("/api/game/save/import", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });

            resetAchievements(); // 🔥 evita desync visual

            location.reload();

        } catch (err) {
            alert("Archivo inválido");
        }
    });

    input.click();
}

// --- NEW GAME ---
function newGame(){
    if(!confirm("¿Seguro que quieres empezar una nueva partida?")){
        return;
    }

    // 🔥 limpiar logros del navegador
    resetAchievements();

    // 🔥 seguridad extra (por si acaso)
    localStorage.removeItem("unlockedAchievements");

    // 🔥 FIX CLAVE
    achievementsReady = false;

    fetch("/api/game/reset",{method:"POST"})
        .then(() => {
            location.reload();
        });
}

// --- GAME STATE ---
async function loadGameState(){
    const response = await fetch("/api/game/state");
    const player = await response.json();

    coins = player.currentCoins;
    document.getElementById("coins").textContent = formatNumber(player.currentCoins);
    document.getElementById("prestigePoints").textContent = player.prestigePoints;
    document.getElementById("prestigeRequirement").textContent = formatNumber(player.prestigeRequirement);

    if(player.skillTree){
        document.getElementById("prestige-skill-points").textContent =
            player.skillTree.availablePrestigePoints;
    }

    // ESTADÍSTICAS
    document.getElementById("stat-clicks").textContent =
        formatNumber(player.totalClicks);

    document.getElementById("stat-cpc").textContent =
        formatNumber(player.coinsPerClick);

    document.getElementById("stat-cps").textContent =
        formatNumber(player.passiveBonus);

    document.getElementById("stat-total").textContent =
        formatNumber(player.totalCoinsEarned);

    document.getElementById("stat-prestige").textContent =
        formatNumber(player.prestigePoints);

    document.getElementById("stat-resets").textContent =
        formatNumber(player.resetsCount);

    // PROGRESO PRESTIGE
    let percent = 0;
    if(player.prestigeRequirement > 0){
        percent = (player.coinsThisRun / player.prestigeRequirement) * 100;
    }
    percent = Math.min(percent,100);
    if(player.coinsThisRun === 0) percent = 0;

    document.getElementById("prestige-progress").style.width = percent + "%";

    const prestigeBtn = document.getElementById("prestige-button");
    if(player.coinsThisRun >= player.prestigeRequirement){
        prestigeBtn.classList.add("active");
    } else {
        prestigeBtn.classList.remove("active");
    }
}

// --- LOGROS ---
async function loadAchievements(){
    const response = await fetch("/api/game/achievements");
    const achievements = await response.json();

    achievementPanel.innerHTML = "";

    if (!achievementsReady) {
        achievements.forEach(a => {
            if (a.unlocked) {
                unlockedAchievements.add(a.name);
            }
        });

        localStorage.setItem(
            "unlockedAchievements",
            JSON.stringify([...unlockedAchievements])
        );

        achievementsReady = true;
    }

    achievements.forEach(a => {

        const id = a.name;

        if (
            achievementsReady &&
            a.unlocked &&
            !unlockedAchievements.has(id)
        ){
            unlockedAchievements.add(id);

            localStorage.setItem(
                "unlockedAchievements",
                JSON.stringify([...unlockedAchievements])
            );

            achievementAudio.pause();
            achievementAudio.currentTime = 0;

            achievementAudio.play().catch(() => {
                setTimeout(() => {
                    achievementAudio.play().catch(() => {});
                }, 50);
            });

            showAchievementToast(a.name, a.description);
        }

        const div = document.createElement("div");
        div.innerHTML = `
            <b>${a.name}</b><br>
            ${a.description}<br>
            Status: ${a.unlocked ? "✅" : "❌"}
            <hr>
        `;
        achievementPanel.appendChild(div);
    });
}

// --- CLICK ---
document.getElementById("coin-button").addEventListener("click", async () => {
    const response = await fetch("/api/game/click",{method:"POST"});
    const player = await response.json();
    document.getElementById("coins").textContent = formatNumber(player.currentCoins);
});

// --- PRESTIGE ---
async function prestige(){
    await fetch("/api/game/prestige",{method:"POST"});
    window.location.href = "skill-tree.html";
}
document.getElementById("prestige-button").addEventListener("click", prestige);

// --- UPGRADES ---
async function loadUpgrades(){
    const response = await fetch("/api/upgrades");
    const upgrades = await response.json();


    const container = document.getElementById("upgrade-list");
    container.innerHTML = "";

    upgrades.forEach((u,index)=>{

        const div = document.createElement("div");
        div.className = "upgrade";
        const currentValue = u.level * u.effectValue;
        div.innerHTML = `
            <b>${u.name}</b><br>

            Nivel: ${u.level}/${u.maxLevel}<br>

            Mejora: +${formatNumber(u.effectValue)} por nivel<br>

            Precio: ${formatNumber(u.cost)} $<br><br>

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

// --- SKILLS ---
async function loadSkills(){
    const container = document.getElementById("skill-tree");
    if(!container) return;

    const response = await fetch("/api/game/skills");
    const skills = await response.json();

    container.innerHTML = "";

    skills.forEach((s,index)=>{
        const div = document.createElement("div");
        div.className = "skill";
        div.innerHTML = `
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

// --- TABS ---
function showTab(tabId){
    document.querySelectorAll(".tab-content").forEach(tab => tab.classList.remove("active"));
    document.querySelectorAll(".tab-button").forEach(btn => btn.classList.remove("active"));

    document.getElementById(tabId).classList.add("active");

    const btn = Array.from(document.querySelectorAll(".tab-button"))
        .find(b => b.getAttribute("onclick").includes(tabId));

    if(btn) btn.classList.add("active");
}

// --- INIT ---
loadGameState();
loadUpgrades();
loadSkills();
loadAchievements();

setInterval(loadGameState, 1000);
setInterval(loadAchievements, 1000);