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
                    <fieldset className="w-full p-4 space-y-4 border border-gray-300 rounded-md dark:text-gray-800">
                        <legend className="text-2xl font-bold bg-white/40 rounded-md backdrop-blur-md">
                            <input
                                name="title"
                                value={editSummary.title}
                                onChange={handleChange}
                                className="w-full p-3 bg-white/40 backdrop-blur-md border border-gray-300 rounded-md focus:outline-none dark:text-gray-800"

                            />
                        </legend>

                    <textarea
                        name="text"
                        value={editSummary.text}
                        onChange={handleChange}
                        rows={10}
                        className={"w-full h-screen p-2 border bg-white/40 backdrop-blur-md border-gray-300 rounded-md resize-none dark:text-gray-800"}
                    />
                    <div className="flex space-x-4 mt-4">
                    <ButtonWithIcon text={"Sichern"} type={"submit"} />
                    <ButtonWithIcon text={"Verwerfen"} onClick={handleBackButtonClick} />
                    </div>
                    </fieldset>
                </form>
            ) : (
                <div>
                    <SummaryCard summary={summary} />
                    <div className="flex space-x-4 mt-4">
                        <ButtonWithIcon text={"Ändern"} onClick={handleEditButtonClick} />
                    </div>
                </div>
            )}
        </>
    )
}
