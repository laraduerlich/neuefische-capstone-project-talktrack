import {useEffect, useState} from "react";
import {getAllSummaries} from "../utils/dataService.ts";
import {Summary} from "../type/Summary.tsx";
import SearchInput from "./SearchInput.tsx";
import {useNavigate} from "react-router-dom";

export default function SummaryList() {

    const navigate = useNavigate();
    const [summaries, setSummaries] = useState<Summary[]>([]);
    const [searchInput, setSearchInput] = useState('');


    // Filtered summaries based on search input
    const filteredSummaries = summaries.filter(summary =>
        summary.title.toLowerCase().includes(searchInput.toLowerCase())
    );

    // Fetch all summaries
    useEffect(() => {
        getAllSummaries()
            .then((data) => {
                console.log("Fetched summaries successfully");
                setSummaries(data);
            })
            .catch((error) => {
                console.error("Error fetching summaries:", error);
            })
    }, []);

    // Button handelers
    const handleNewSummaryButtonClick = () => {
        navigate("summary/new");
    }

    return (
        <>
            <div>
                <div>
                    {/* Search input and new summary button */}
                    <SearchInput value={searchInput} onChange={setSearchInput} />
                    <button
                        onClick={handleNewSummaryButtonClick}>
                        <span>Neue Zusammenfassung</span>
                    </button>
                </div>
                <div>
                    {/* List of all summaries */}
                    <ul>
                        {filteredSummaries.map((summary) => (
                            <li
                                key={summary.id}>
                                <span>
                                {summary.title}
                                </span>
                                <div>
                                    {/* Button für delete & view */}
                                </div>
                            </li>
                            ))}
                    </ul>
                </div>
            </div>
        </>
    )
}
