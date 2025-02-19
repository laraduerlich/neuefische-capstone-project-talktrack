import {useEffect, useState} from "react";
import {deleteSummaryById, getAllSummaries} from "../utils/dataService.ts";
import {Summary} from "../type/Summary.tsx";
import SearchInput from "./SearchInput.tsx";
import {useNavigate} from "react-router-dom";
import ButtonWithIcon from "./ButtonWithIcon.tsx";

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
        navigate("/summary/new");
    }

    const handleViewButtonClick = (id: string | undefined) => {
        if (id) {
            console.log("id in summary:", id)
            navigate(`/summary/${id}`); // Use path parameter instead of query parameter
        } else {
            console.error("Invalid ID for viewing shopping list.");
        }
    };

    const handleDeleteButtonClick = (id: string | undefined) => {
        deleteSummaryById(id)
            .then(() => {
                console.log(`Summary with id ${id} deleted successfully.`);
                // Reload of all suammries after delete
                getAllSummaries()
                    .then((data) => {
                        console.log("Fetched summaries successfully after delete.");
                        setSummaries(data);
                    })
                    .catch((error) => {
                        console.error("Error fetching summaries after delete:", error);
                    });
            })
    }

    return (
        <>
            <div>
                <div className="flex flex-col sm:flex-row items-center w-full max-w-3xl mx-auto my-6 space-y-4 sm:space-y-0 sm:space-x-4">
                    {/* Search input and new summary button */}
                    <SearchInput value={searchInput} onChange={setSearchInput} />
                    <button
                        onClick={handleNewSummaryButtonClick}
                        className="w-full sm:w-auto h-12 px-5 py-3 text-sm font-semibold text-white bg-indigo-500 rounded-lg shadow-md hover:bg-indigo-600 flex items-center justify-center space-x-2 min-w-[200px]"
                        >
                        <span>Neue Zusammenfassung</span>
                    </button>
                </div>
                <div className="space-y-4">
                    {/* List of all summaries */}
                    <ul className="space-y-4">
                        {filteredSummaries.map((summary) => (
                            <li
                                key={summary.id}
                                className="flex flex-col sm:flex-row sm:items-center sm:justify-between p-6 bg-white/40 rounded-md backdrop-blur-md hover:bg-white/60">
                                <span className="text-lg font-semibold text-gray-800 text-center sm:text-left">
                                {summary.title}
                                </span>
                                <div className="flex space-x-4">
                                    {/* Button für delete & view */}
                                    <ButtonWithIcon
                                        onClick={() => handleViewButtonClick(summary.id)}
                                        icon={"/view-icon.png"}
                                    />
                                    <ButtonWithIcon
                                        onClick={() => handleDeleteButtonClick(summary.id)}
                                        icon={"/delete-icon.png"}
                                    />
                                </div>
                            </li>
                            ))}
                    </ul>
                </div>
            </div>
        </>
    )
}
