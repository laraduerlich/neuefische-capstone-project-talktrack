import {useParams} from "react-router-dom";
import {ChangeEvent, useEffect, useState} from "react";
import {getSummaryById, updateSummary} from "../utils/dataService.ts";
import {Summary} from "../type/Summary.tsx";
import SummaryCard from "../component/SummaryCard.tsx";
import ButtonWithIcon from "../component/ButtonWithIcon.tsx";

export default function SummaryPage() {

    const { id } = useParams<{ id: string }>();
    const [summary, setSummary] = useState<Summary | null>(null);
    const [loading, setLoading] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [editSummary, setEditSummary] = useState<Summary | null>(summary);


    useEffect(() => {
        if (id) {
            setLoading(true)
            getSummaryById(id)
                .then((data: Summary) => {
                    setSummary(data);
                })
                .catch((error) => {
                    console.error("Summary could not get fetched", error);
                })
                .finally(() =>{
                    setLoading(false)
                })
        }
    }, [id]);

    // to fetch the data for editing
    useEffect(() => {
        setEditSummary(summary)
    }, [summary]);

    // Handler
    const handleChange = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        if (!editSummary) return;
        setEditSummary({
            ...editSummary,
            [event.target.name]: event.target.value,
        });
    };

    const handleSaveButtonClick = (event) => {
        event.preventDefault();
        if (!editSummary) {
            alert('Bitte überprüfe deine Eingabe!');
            return;
        }
        updateSummary(editSummary)
            .then((updatedSummary: Summary) => {
                setSummary(updatedSummary)
                setIsEditing(false)
            })
            .catch((error) => {
                console.error("Error after Change", error)
            });
    }

    const handleBackButtonClick = () => {
        setIsEditing(false)
    }

    const handleEditButtonClick = () => {
        setIsEditing(true)
    }


    // Handling when data is loading or return null
    if (loading) return <p>Lade Daten...</p>;
    if (!editSummary) return <p>Fehler beim Laden</p>

    return (
        <>
            {isEditing ? (
                <form onSubmit={handleSaveButtonClick}>
                    <input
                        name="title"
                        value={editSummary.title}
                        onChange={handleChange}
                    />
                    <textarea
                        name="text"
                        value={editSummary.text}
                        onChange={handleChange}
                    />
                    <ButtonWithIcon text={"Sichern"} type={"submit"} />
                    <ButtonWithIcon text={"Verwerfen"} onClick={handleBackButtonClick} />
                </form>
            ) : (
                <div>
                    <SummaryCard summary={summary} />
                    <ButtonWithIcon text={"Ändern"} onClick={handleEditButtonClick} />
                </div>
            )}
        </>
    )
}
