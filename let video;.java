let video;
let classifier;
let label = 'waiting...';
let confidence = 0;

// STEP 1: Load the model!
let modelURL = 'https://teachablemachine.withgoogle.com/models/lsBrn0MsP/';
function preload() {
  classifier = ml5.imageClassifier(modelURL + 'model.json');
}
function setup() {
  createCanvas(640, 520);
  video = createCapture(VIDEO);
  video.size(640, 520);
  video.hide();

  // STEP 2: Start classifying
  classifyVideo();
}

function draw() {
  background(0);
  image(video, 0, 0);

  // STEP 4: Draw the label
  fill(255);
  textSize(32);
  textAlign(CENTER, CENTER);
  text(label + ' (' + nf(confidence * 100, 2, 1) + '%)', width / 2, height - 16);}

// STEP 3: Get classification result
function classifyVideo() {
  classifier.classify(video, gotResults);
}

function gotResults(error, results) {
  if (error) {
    console.error(error);
    return;
  }
