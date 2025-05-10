# Price Grid Creator

Assessment project provided by **Nila Apps Private Limited**.

This full-stack web application displays a dynamic, editable price grid that allows users to add rows and columns, fetch price data from a Java Spring Boot backend, and interact through a clean and responsive Next.js UI.

---

## 📦 Tech Stack

- **Frontend**: [Next.js](https://nextjs.org/), Tailwind CSS, Axios
- **Backend**: [Spring Boot](https://spring.io/projects/spring-boot) (Java 21)
- **Data Store**: In-memory h2-database
- **API**: RESTful endpoint (`/api/prices`)

---
## 🚀 Run Instructions

### 🛠️ Prerequisites
Ensure you have the following installed:
- **Java 21+** (for the Spring Boot backend)
- **Maven** (wrapper included)
- **Node.js 22+** and **npm** (for the Next.js frontend)

---

### 📁 Project Structure

After cloning the repository, you'll find two main folders:

1. `api` – Spring Boot project (backend API)
2. `price_grid_app_ui` – Next.js project (frontend UI)

---

### 🔧 Running the Backend (Spring Boot)

1. Open the `api` folder in VS Code (or any IDE).
2. Open the terminal in that directory.
3. Run the following command to start the backend:

   ```bash
   mvnw spring-boot:run
   ```
4. The API will be available at:
👉 http://localhost:8080/api/prices

## Folder Structure
<img width="289" alt="image" src="https://github.com/user-attachments/assets/be644ab7-d0f5-43cf-a47d-d5c118b1a81f" />



### 💻 Running the Frontend (Next.js)

1.Open the price_grid_app_ui folder in VS Code.
2. In the terminal, install dependencies by running this command:
  ```bash
     npm install
  ```
3. Start the development server:
   ```
      npm run dev
   ```
4. The app will be accessible at:
  👉 http://localhost:3000

## Folder Structure:
<img width="175" alt="image" src="https://github.com/user-attachments/assets/6bf5e535-9f64-4e45-9eeb-f0016f620523" />


You're now ready to use the Price Grid Creator UI with live data from the backend API.
   
## 🚀 Features

- ✅ Responsive and clean UI grid
- ✅ Add new rows (Height To) and columns (Width To)
- ✅ Edit individual price cells with a click (no clutter)
- ✅ Fetch and display data from the backend API
- ✅ Error handling for API issues
- ✅ In-memory data persistence (server-side)
- ✅ Works across modern browsers (Chrome, Firefox, Edge)
- ✅ Fully responsive (desktop & mobile views)
- ❌ (Optional) Drag-and-drop support – **not implemented yet**

---

### 📸 Screenshots

## UI Screenshots:
<img width="953" alt="image" src="https://github.com/user-attachments/assets/80bead2f-c2b4-4716-9dfb-01aff9c70d8e" />

### Updating Cell Value
![image](https://github.com/user-attachments/assets/3071b425-414b-4912-823b-225b9f5cec65)

### Updating Column Header
![image](https://github.com/user-attachments/assets/bf74deeb-55f7-4587-a754-967976d10931)

### Updating Row Header
![image](https://github.com/user-attachments/assets/49048f63-023d-4fea-9d0c-67995b4b3fd0)

### New Row Added
![image](https://github.com/user-attachments/assets/bcd8e4a7-607b-43f0-bd11-8a88c3cf3497)

### New Column Added
![image](https://github.com/user-attachments/assets/8d50accb-9753-4616-9403-005321605b2e)

### With Delete Option
![image](https://github.com/user-attachments/assets/4a7e8008-357d-421c-b944-4addfd12cd6b)




### /api/prices - GET
<img width="939" alt="image" src="https://github.com/user-attachments/assets/708e7af3-c8bf-48f5-8b38-c9f56b66f225" />

### /api/widths - GET
<img width="946" alt="image" src="https://github.com/user-attachments/assets/bbbdd192-47d1-4040-8160-497d555f5447" />

### /api/heights - GET
<img width="952" alt="image" src="https://github.com/user-attachments/assets/a3b26b8d-7967-435d-9bf9-a351cf9db50f" />

### Networks Tab on GET API:
<img width="951" alt="image" src="https://github.com/user-attachments/assets/36107305-b3c5-44c6-ba11-5e2d9ff42c5e" />



  



