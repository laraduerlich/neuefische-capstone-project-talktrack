import axios from "axios";
import {Summary} from "../type/Summary.tsx";


// Create new Summary
export const createSummary = async (formData: FormData) => {
    try {
        const response = await axios.post<{ id: string }>("/api/upload", formData, {
            headers: { "Content-Type": "multipart/form-data" },
            timeout: 10000000
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
            text: response.data.text,
        }
        return summary;
    } catch (error) {
        console.error("Kein Summary mit der Id gefunden", error);
        throw error;
    }
}

// Get all summaries
export const getAllSummaries = async () => {
    try {
        const response = await axios.get("/api/summaries");
        return response.data as Summary[];
    } catch (error) {
        console.error("Fehler beim Abrufen der Summaries:", error);
        throw error;
    }
}

// Delete summary by id
export const deleteSummaryById = async (id: string | undefined): Promise<string> => {
    try {
        const response = await axios.delete(`/api/summary/${id}`);
        console.log(response.data)
        return response.data;
    } catch (error) {
        console.error("Kein Summary mit der Id gefunden", error);
        throw error;
    }
}

// Edit a summary
export const updateSummary = async (summary: Summary) => {
    try {
        const response = await axios.put(`/api/summary/${summary.id}`, summary);
        const updatedSummary: Summary = {
            id: response.data.id,
            title: response.data.title,
            text: response.data.text
        }
        return updatedSummary
    } catch (error) {
        console.error("Summary konnte nicht geändert werden", error);
        throw error;
    }
}
