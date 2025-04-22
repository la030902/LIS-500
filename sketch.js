let video;
let classifier;
let label = 'waiting...';
let stopImg, yieldImg, streetImg;

function preload() {
  classifier = ml5.imageClassifier('https://teachablemachine.withgoogle.com/models/bXy2kDNi/model.json');
  stopImg   = loadImage("images/stop.webp");
  yieldImg  = loadImage("images/yield.jpg");
  streetImg = loadImage("images/street.jpg");
}

function modelReady() {
  console.log('Model Loaded!');
  classifyVideo();
}

function setup() {
  let canvas = createCanvas(640, 520);
  canvas.parent("canvas-container");
  video = createCapture(VIDEO);
  video.size(640, 520);
  video.hide();
  classifyVideo();
}

function draw() {
  background(0);
  image(video, 0, 0);

  fill(255);
  textSize(32);
  textAlign(CENTER, CENTER);
  text(label, width / 2, height - 16);
  textFont("Poppins", sans-serif, 12);

  if (label === "Stop") {
    image(stopImg, 100, 100, 100, 100);
  } else if (label === "Yield") {
    image(yieldImg, 100, 100, 100, 100);
  } else if (label === "Street") {
    image(streetImg, 100, 100, 100, 100);
  }
}

function classifyVideo() {
  classifier.classify(video, gotResults);
}

function gotResults(error, results) {
  if (error) {
    console.error(error);
    return;
  }
  label = results[0].label;
  classifyVideo();
}
