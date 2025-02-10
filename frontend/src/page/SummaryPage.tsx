import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {getSummaryById} from "../utils/dataService.ts";
import {Summary} from "../type/Summary.tsx";
import SummaryCard from "../component/SummaryCard.tsx";

export default function SummaryPage() {

    const { id } = useParams<{ id: string }>();
    const [summary, setSummary] = useState<Summary | null>(null);
    const [loading, setLoading] = useState(false);


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

    if (loading) return <p>Lade Daten...</p>;

    return (
        <>
            <SummaryCard summary={summary} />
        </>
    )
}
