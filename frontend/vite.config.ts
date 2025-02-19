import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import path from "path";

export default defineConfig({
    plugins: [react()],
    css: {
        postcss: path.resolve(__dirname, "./postcss.config.js"), // Point to your PostCSS config
    },
    server: {
        proxy: {
            "/api": {
                target: "http://localhost:8080", // Your backend server
                changeOrigin: true, // Ensures the Origin header matches the target
                rewrite: (path) => path.replace(/^\/api/, "/api"), // Optional: adjust path if needed
            },
        },
    },
});
