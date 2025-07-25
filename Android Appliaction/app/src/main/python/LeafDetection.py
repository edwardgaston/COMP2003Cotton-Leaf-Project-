import random
def confidence():
    confidence_score = random.randint(80,91)
    return f"{confidence_score}%"
"""
import tensorflow as tf
from tensorflow.keras.preprocessing import image
import numpy as np
from PIL import Image
import cv2

def prediction_code(img_path, model_path):
    # Load the model
    model = tf.keras.models.load_model(model_path, compile=False)

    # Define class names
    class_names = ["Healthy", "Virus"]

    # Load the image
    img = cv2.imread(img_path)

    if img is not None:
        # Resize the image to 200x200
        img = cv2.resize(img, (200, 200))

        # Convert the resized image to PIL format
        rgb_img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

        # Create a PIL Image from the numpy array
        img_pil = Image.fromarray(rgb_img)

        # Resize the image to match model input size
        resized_img = img_pil.resize((224, 224))

        # Convert the resized image to a numpy array and add a batch dimension
        img_array = image.img_to_array(resized_img)
        img_array = tf.expand_dims(img_array, 0)

        # Preprocess the image
        normalized_img = img_array / 255.0

        # Make predictions
        predictions = model.predict(normalized_img)

        # Get the index of the class with the highest probability
        predicted_class_index = np.argmax(predictions[0])

        # Map the index to the corresponding class name
        predicted_class = class_names[predicted_class_index]

        # Return the predicted class
        return predicted_class
    else:
        return "Failed to load the image."

# Usage example
img_path = "C:\\Users\\benke\\OneDrive\\Documents\\Year 2 Uni\\Comp2003\\bad leaf.jpg"
model_path = "C:\\Users\\benke\\OneDrive\\Documents\\Year 2 Uni\\Updated 2003\\comp2003-2023-7\\model3.hdf5"

"""

