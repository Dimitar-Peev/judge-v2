document.addEventListener('DOMContentLoaded', function () {
    const scoreInput = document.getElementById('score');
    const bubble = document.getElementById('range-bubble');

    if (!scoreInput || !bubble) return;

    function updateBubble() {
        const val = Number(scoreInput.value);
        const min = Number(scoreInput.min || 0);
        const max = Number(scoreInput.max || 100);

        const percent = ((val - min) * 100) / (max - min);

        bubble.textContent = `${val}%`;

        bubble.style.left = `calc(${percent}% + (${8 - percent * 0.15}px))`;
    }

    scoreInput.addEventListener('input', updateBubble);

    updateBubble();
});