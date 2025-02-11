import {useParams} from "react-router-dom";
import {ChangeEvent, DetailedHTMLProps, FormEvent, InputHTMLAttributes, useEffect, useState} from "react";
import {editSummary, getSummaryById} from "../utils/dataService.ts";
import {Summary} from "../type/Summary.tsx";
import SummaryCard from "../component/SummaryCard.tsx";
import ButtonWithIcon from "../component/ButtonWithIcon.tsx";

export default function SummaryPage() {

    const { id } = useParams<{ id: string }>();
    const [summary, setSummary] = useState<Summary | null>(null);
    const [loading, setLoading] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [editValues, setEditValues] = useState<Summary | null>(summary);


    useEffect(() => {
        if (id) {
            setLoading(true)
            getSummaryById(id)
                .then((data: Summary) => {
                    setSummary(data);
                })
                .catch((error) => {
                    console.error("Zusammenfassung konnte nicht geladen werden", error);
                })
                .finally(() =>{
                    setLoading(false)
                })
        }
    }, [id]);

    const handleChange = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        if (!editValues) return;
        setEditValues({
            ...editValues,
            [event.target.name]: event.target.value,
        });
    };

    // Button handler
    const handleSaveButtonClick = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!editValues) {
            alert('Bitte überprüfe deine Eingabe!');
            return;
        }
        const i = editSummary(editValues)


    }

    const handleBackButtonClick = () => {
        setIsEditing(false)
    }

    const handleEditButtonClick = () => {
        setIsEditing(true)
    }


    // Handling when data is loading or return null
    if (loading) return <p>Lade Daten...</p>;
    if (editValues === null) return <p>Keine Zusammenfassung gefunden</p>

    return (
        <>
            <SummaryCard summary={summary} />
            {isEditing ? (
                <form onSubmit={handleSaveButtonClick}>
                    <input
                        name="title"
                        value={editValues.title}
                        onChange={handleChange}
                    />
                    <textarea
                        name="text"
                        value={editValues.text}
                        onChange={handleChange}
                    />
                    <ButtonWithIcon text={"Sichern"} onClick={handleSaveButtonClick} />
                    <ButtonWithIcon text={"Verwerfen"} onClick={handleBackButtonClick} />
                </form>
            ) : (
                <ButtonWithIcon text={"Ändern"} onClick={handleEditButtonClick} />
            )}
        </>
    )
}
