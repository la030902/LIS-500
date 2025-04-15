<script>
  const URL = "https://teachablemachine.withgoogle.com/models/lsBrn0MsP/";

  let model, webcam, labelContainer, maxPredictions;

  // Load the image model and setup the webcam
  async function init() {
    const modelURL = URL + "model.json";
    const metadataURL = URL + "metadata.json";

    // Show loading message
    labelContainer = document.getElementById("label-container");
    labelContainer.innerHTML = "<div>Loading model...</div>";

    // Load the model and metadata
    model = await tmImage.load(modelURL, metadataURL);
    maxPredictions = model.getTotalClasses();

    // Setup webcam
    const flip = true; // whether to flip the webcam
    webcam = new tmImage.Webcam(400, 400, flip); // width, height, flip
    await webcam.setup(); // request access to the webcam
    await webcam.play();
    
    // Append webcam to container
    document.getElementById("webcam-container").appendChild(webcam.canvas);
    
    // Update loading message
    labelContainer.innerHTML = "";
    for (let i = 0; i < maxPredictions; i++) {
      labelContainer.appendChild(document.createElement("div"));
    }
    
    // Start prediction loop
    window.requestAnimationFrame(loop);
  }

  async function loop() {
    webcam.update(); // update the webcam frame
    await predict();
    window.requestAnimationFrame(loop);
  }

  // Run the webcam image through the image model
  async function predict() {
    // Predict can take in an image, video or canvas HTML element
    const prediction = await model.predict(webcam.canvas);
    for (let i = 0; i < maxPredictions; i++) {
      const classPrediction =
        prediction[i].className + ": " + 
        (prediction[i].probability * 100).toFixed(2) + "%";

      // Change label text to emoji and class name based on the prediction
      if (prediction[i].className === 'Street sign') {
        labelContainer.childNodes[i].innerHTML = '🚧 ' + classPrediction;
      } else if (prediction[i].className === 'Yield sign') {
        labelContainer.childNodes[i].innerHTML = '⚠️ ' + classPrediction;
      } else if (prediction[i].className === 'Stop sign') {
        labelContainer.childNodes[i].innerHTML = '🛑 ' + classPrediction;
      } else {
        labelContainer.childNodes[i].innerHTML = classPrediction;
      }
    }
  }

  // Call init to load the model and start the prediction process
  init();
</script>
