// Set data-text on every article h1 so the scanline/glitch pseudo-elements
// in main.css can duplicate the text.
(function () {
  function applyGlitch() {
    document.querySelectorAll('article.md-content__inner h1, .md-typeset h1').forEach(function (h) {
      if (h.hasAttribute('data-text')) return;
      // Grab the visible heading text, excluding the permalink (¶) anchor
      var text = '';
      h.childNodes.forEach(function (n) {
        if (n.nodeType === Node.TEXT_NODE) {
          text += n.textContent;
        } else if (n.nodeType === Node.ELEMENT_NODE && !n.classList.contains('headerlink')) {
          text += n.textContent;
        }
      });
      text = text.trim();
      if (text) h.setAttribute('data-text', text);
    });
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', applyGlitch);
  } else {
    applyGlitch();
  }
})();
