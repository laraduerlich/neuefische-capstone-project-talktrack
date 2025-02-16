import {Summary} from "../type/Summary.tsx";

export type SummaryCardProps = {
    summary: Summary | null
}

export default function SummaryCard({summary}: SummaryCardProps){

    return (
        <>
            <div className="flex space-x-4">
                <fieldset className="w-full p-4 space-y-4 border border-gray-300 rounded-md dark:text-gray-800">
                    <legend className="text-2xl font-bold">{summary?.title}</legend>
                    <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between p-6 bg-white/40 rounded-md backdrop-blur-md dark:text-gray-800">
                        <p>{summary?.text}</p>
                    </div>
                </fieldset>
            </div>
        </>
    )
}
