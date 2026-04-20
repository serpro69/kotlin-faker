let panzoomScrollPosition = 0;

function minimize(instance, box, max, min) {
  box.classList.remove("panzoom-fullscreen");
  max.classList.remove("panzoom-hidden");
  min.classList.add("panzoom-hidden");
  panzoom_reset(instance)
  setTimeout(() => {
    window.scrollTo(0, panzoomScrollPosition);
  }, 0);
}

function maximize(instance, box, max, min) {
  panzoomScrollPosition =
    window.pageYOffset || document.documentElement.scrollTop;

  box.classList.add("panzoom-fullscreen");
  max.classList.add("panzoom-hidden");
  min.classList.remove("panzoom-hidden");
}

function escapeFullScreen(e, box, max, min, instance) {

  if (e.keyCode == 27) {
    minimize(instance, box, max, min);
  }
}

function panzoom_reset(instance) {
  instance.moveTo(0, 0);
  instance.zoomAbs(0, 0, 1);
}

function add_buttons(box, instance) {
  let reset = box.querySelector(".panzoom-reset");
  let max = box.querySelector(".panzoom-max");
  let min = box.querySelector(".panzoom-min");
  let info = box.querySelector(".panzoom-info");
  let info_box = box.querySelector(".panzoom-info-box");

  reset.addEventListener("click", function (e) {
    // instance.moveTo(0, 0);
    // instance.zoomAbs(0, 0, 1);
    panzoom_reset(instance);
  });
  if (info != undefined) {
    info.addEventListener("click", function (e) {
      if (box.dataset.info == "true") {
        box.dataset.info = false;
        info_box.classList.add("panzoom-hidden");
      } else {
        box.dataset.info = true;
        info_box.classList.remove("panzoom-hidden");
      }
    });
  }
  if (max != undefined) {
    max.addEventListener("click", function (e) {
      maximize(instance, box, max, min);
    });
  }
  if (min != undefined) {
    min.addEventListener("click", function (e) {
      minimize(instance, box, max, min);
    });
  }
  box.addEventListener("keydown", function (e) {
    escapeFullScreen(e, box, max, min, instance);
  });
}

function activate_zoom_pan() {
  boxes = document.querySelectorAll(".panzoom-box");

  meta_tag = document.querySelector('meta[name="panzoom-data"]');

  selectors = JSON.parse(meta_tag.content).selectors;

  boxes.forEach((box) => {
    key = box.dataset.key;

    selectors.every((selector) => {
      elem = box.querySelector(selector);

      if (elem != undefined) {
        return false;
      }
      return true;
    });

    if (elem == undefined) {
      return;
    }

    if (
      (elem.nodeName == "DIV" || elem.nodeName == "IMG") &&
      !elem.dataset.zoom
    ) {
      elem.dataset.zoom = true;
      let instance = panzoom(elem, {
        minZoom: 0.5,
        beforeWheel: function (e) {
          switch (key) {
            case "ctrl":
              return !e.ctrlKey;
            case "shift":
              return !e.shiftKey;
            case "alt":
              return !e.altKey;
            default:
              return false && !e.button == 1;
          }
        },
        beforeMouseDown: function (e) {
          switch (key) {
            case "ctrl":
              return !e.ctrlKey && !e.button == 1;
            case "shift":
              return !e.shiftKey && !e.button == 1;
            case "alt":
              return !e.altKey && !e.button == 1;
            default:
              return false && !e.button == 1;
          }
        },
        zoomDoubleClickSpeed: 1,
      });
      add_buttons(box, instance);
    }
  });
}

// handle themes differently
let pz_theme = document.querySelector('meta[name="panzoom-theme"]').content;

if (pz_theme == "material") {
  document$.subscribe(function () {
    const interval = setInterval(activate_zoom_pan, 1000);

    setTimeout(function () {
      clearInterval(interval);
    }, 5000);
  });
} else {
  const interval = setInterval(activate_zoom_pan, 1000);

  setTimeout(function () {
    clearInterval(interval);
  }, 5000);
}
