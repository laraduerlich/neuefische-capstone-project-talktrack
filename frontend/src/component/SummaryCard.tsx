import {Summary} from "../type/Summary.tsx";

export type SummaryCardProps = {
    summary: Summary | null
}

export default function SummaryCard({summary}: SummaryCardProps){

    return (
        <>
            <div>
                <h2>{summary?.title}</h2>
                <p>{summary?.text}</p>
            </div>
        </>
    )
}
