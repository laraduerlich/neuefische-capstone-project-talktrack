import {Summary} from "../type/Summary.tsx";

export type SummaryCardProps = {
    summary: Summary | null
}

export default function SummaryCard({summary}: SummaryCardProps){

    return (
        <>
            <div>
                <h2>Title: {summary?.title}</h2>
                <p>Zusammenfassung: {summary?.text}</p>
            </div>
        </>
    )
}
