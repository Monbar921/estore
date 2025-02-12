# Use Node.js as the base image
FROM node:18

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install --legacy-peer-deps

# Copy the entire app to the container
COPY . .

# Expose the port the React dev server runs on
EXPOSE 3000

# Start the development server
CMD ["npm", "start"]
