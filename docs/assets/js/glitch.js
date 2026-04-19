// Set data-text on every article h1 so the scanline/glitch pseudo-elements
// in main.css can duplicate the text. Also toggles `.glitch-on` via an
// IntersectionObserver so the animation only runs while the h1 is visible.
(function () {
  function extractText(el) {
    var text = '';
    el.childNodes.forEach(function (n) {
      if (n.nodeType === Node.TEXT_NODE) {
        text += n.textContent;
      } else if (n.nodeType === Node.ELEMENT_NODE && !n.classList.contains('headerlink')) {
        text += n.textContent;
      }
    });
    return text.trim();
  }

  function applyGlitch() {
    var headings = document.querySelectorAll('article.md-content__inner h1, .md-typeset h1');
    if (!headings.length) return;

    headings.forEach(function (h) {
      if (!h.hasAttribute('data-text')) {
        var text = extractText(h);
        if (text) h.setAttribute('data-text', text);
      }
    });

    // If the user prefers reduced motion, CSS already hides the glitch layers.
    // Skip the observer — no animation means nothing to pause.
    if (window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches) {
      return;
    }

    if (typeof IntersectionObserver === 'undefined') {
      // Very old browser: just leave it running by marking all as visible.
      headings.forEach(function (h) { h.classList.add('glitch-on'); });
      return;
    }

    var io = new IntersectionObserver(function (entries) {
      entries.forEach(function (e) {
        e.target.classList.toggle('glitch-on', e.isIntersecting);
      });
    }, { rootMargin: '50px' });

    headings.forEach(function (h) { io.observe(h); });
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', applyGlitch);
  } else {
    applyGlitch();
  }
})();
