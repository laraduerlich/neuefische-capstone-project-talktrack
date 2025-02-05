import axios from "axios";
import {Summary} from "../type/Summary.tsx";


// Create new Summary
export const createSummary = async (formData: FormData) => {
    try {
        const response = await axios.post<{ id: string }>("/api/upload", formData, {
            headers: { "Content-Type": "multipart/form-data" },
        });
        console.log("Upload erfolgreich, Summary ID:", response.data);
        return response.data;
    } catch (error) {
        console.error("Fehler beim Hochladen:", error);
        return "Kein ID gefunden";
    }
}

// Get a summary by id
export const getSummaryById = async (id: string | undefined): Promise<Summary> => {
    if (id === undefined) {
        throw new Error("ID is undefined");
    }
    try {
        const response = await axios.get(`/api/summary/${id}`);
        const summary: Summary = {
            id: response.data.id,
            title: response.data.title,
            text: response.data.transcription
        }
        return summary;
    } catch (error) {
        console.error("Kein Summary mit der Id gefunden", error);
        throw error;
    }
}