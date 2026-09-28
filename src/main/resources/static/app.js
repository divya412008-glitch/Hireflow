const STAGES = [
    "SCREENING",
    "INTERVIEW",
    "OFFER",
    "HIRED"
];


async function fetchJson(url) {

    const response = await fetch(url);

    if (!response.ok) {
        throw new Error(`Request failed: ${response.status}`);
    }

    return response.json();
}


/* =========================================================
   LOAD EVERYTHING
========================================================= */

async function loadDashboard() {

    try {

        const [
            jobs,
            candidates,
            applications,
            interviews,
            funnel
        ] = await Promise.all([
            fetchJson("/api/jobs"),
            fetchJson("/api/candidates"),
            fetchJson("/api/applications"),
            fetchJson("/api/interviews"),
            fetchJson("/api/applications/funnel/1")
        ]);


        updateMetrics(
            jobs,
            candidates,
            applications,
            interviews
        );


        updateFunnel(
            funnel
        );


        updateApplications(
            applications
        );


        updateInterviews(
            interviews
        );


        updateJobs(
            jobs,
            applications
        );


        updateActivity(
            applications,
            interviews
        );


    } catch (error) {

        console.error(
            "HireFlow dashboard error:",
            error
        );

    }

}


/* =========================================================
   METRICS
========================================================= */

function updateMetrics(
    jobs,
    candidates,
    applications,
    interviews
) {

    document.getElementById(
        "jobCount"
    ).textContent = jobs.length;


    document.getElementById(
        "candidateCount"
    ).textContent = candidates.length;


    document.getElementById(
        "applicationCount"
    ).textContent = applications.length;


    document.getElementById(
        "interviewCount"
    ).textContent = interviews.length;


    document.getElementById(
        "sidebarHiringCount"
    ).textContent = applications.length;

}


/* =========================================================
   FUNNEL
========================================================= */

function updateFunnel(funnel) {

    const screening =
        funnel.SCREENING || 0;

    const interview =
        funnel.INTERVIEW || 0;

    const offer =
        funnel.OFFER || 0;

    const hired =
        funnel.HIRED || 0;


    document.getElementById(
        "screeningCount"
    ).textContent = screening;


    document.getElementById(
        "interviewStageCount"
    ).textContent = interview;


    document.getElementById(
        "offerCount"
    ).textContent = offer;


    document.getElementById(
        "hiredCount"
    ).textContent = hired;


    const max =
        Math.max(
            screening,
            interview,
            offer,
            hired,
            1
        );


    document.getElementById(
        "screeningBar"
    ).style.width =
        `${(screening / max) * 100}%`;


    document.getElementById(
        "interviewBar"
    ).style.width =
        `${(interview / max) * 100}%`;


    document.getElementById(
        "offerBar"
    ).style.width =
        `${(offer / max) * 100}%`;


    document.getElementById(
        "hiredBar"
    ).style.width =
        `${(hired / max) * 100}%`;

}


/* =========================================================
   APPLICATION TABLE
========================================================= */

function updateApplications(
    applications
) {

    const tbody =
        document.getElementById(
            "applicationTable"
        );


    if (!applications.length) {

        tbody.innerHTML = `
            <tr>
                <td
                    colspan="4"
                    class="table-loading">

                    No applications available.

                </td>
            </tr>
        `;

        return;
    }


    tbody.innerHTML =
        applications
            .slice(0, 6)
            .map(application => {

                const candidate =
                    application.candidate || {};

                const job =
                    application.job || {};

                const stage =
                    application.pipelineStage?.name
                    || "UNKNOWN";


                const initial =
                    candidate.name
                        ? candidate.name
                            .charAt(0)
                            .toUpperCase()
                        : "?";


                const applied =
                    application.appliedAt
                        ? new Date(
                            application.appliedAt
                        ).toLocaleDateString(
                            [],
                            {
                                day: "2-digit",
                                month: "short"
                            }
                        )
                        : "-";


                return `

                    <tr>

                        <td>

                            <div
                                class="candidate-cell">

                                <div
                                    class="table-avatar">

                                    ${initial}

                                </div>

                                <span
                                    class="candidate-name">

                                    ${candidate.name
                                        || "Unknown"}

                                </span>

                            </div>

                        </td>


                        <td>
                            ${job.title || "—"}
                        </td>


                        <td>

                            <span
                                class="stage-badge">

                                ${stage}

                            </span>

                        </td>


                        <td>
                            ${applied}
                        </td>

                    </tr>

                `;

            })
            .join("");

}


/* =========================================================
   INTERVIEWS
========================================================= */

function updateInterviews(
    interviews
) {

    const container =
        document.getElementById(
            "interviewTimeline"
        );


    if (!interviews.length) {

        container.innerHTML = `
            <div class="table-loading">
                No upcoming interviews.
            </div>
        `;

        return;
    }


    const sorted =
        [...interviews]
            .sort(
                (a, b) =>
                    new Date(
                        a.startTime
                    ) -
                    new Date(
                        b.startTime
                    )
            )
            .slice(0, 5);


    container.innerHTML =
        sorted.map(interview => {

            const date =
                new Date(
                    interview.startTime
                );


            const time =
                date.toLocaleTimeString(
                    [],
                    {
                        hour: "2-digit",
                        minute: "2-digit"
                    }
                );


            const candidate =
                interview.application
                    ?.candidate
                    ?.name
                || "Candidate";


            const type =
                interview.interviewType
                || "Interview";


            return `

                <div
                    class="timeline-item">

                    <div
                        class="timeline-time">

                        ${time}

                    </div>


                    <div
                        class="timeline-content">

                        <strong>
                            ${candidate}
                        </strong>

                        <span>
                            ${type}
                            · Interview #${interview.id}
                        </span>

                    </div>

                </div>

            `;

        }).join("");

}


/* =========================================================
   JOB PERFORMANCE
========================================================= */

function updateJobs(
    jobs,
    applications
) {

    const container =
        document.getElementById(
            "jobPerformance"
        );


    if (!jobs.length) {

        container.innerHTML = `
            <div class="table-loading">
                No open positions.
            </div>
        `;

        return;
    }


    container.innerHTML =
        jobs.slice(0, 5).map(job => {

            const count =
                applications.filter(
                    application =>
                        application.job?.id === job.id
                ).length;


            const initial =
                job.title
                    ? job.title
                        .charAt(0)
                        .toUpperCase()
                    : "J";


            return `

                <div class="job-row">

                    <div class="job-logo">
                        ${initial}
                    </div>


                    <div>

                        <strong>
                            ${job.title}
                        </strong>

                        <span>
                            ${job.location || "Remote"}
                        </span>

                    </div>


                    <div class="job-count">
                        ${count} applications
                    </div>

                </div>

            `;

        }).join("");

}


/* =========================================================
   ACTIVITY FEED
========================================================= */

function updateActivity(
    applications,
    interviews
) {

    const container =
        document.getElementById(
            "activityFeed"
        );


    const activity = [];


    applications
        .slice(0, 3)
        .forEach(application => {

            activity.push({
                icon: "◉",
                title:
                    `${application.candidate?.name
                        || "Candidate"} applied`,
                detail:
                    application.job?.title
                    || "Job opening",
                time: "Recent"
            });

        });


    interviews
        .slice(0, 2)
        .forEach(interview => {

            activity.push({
                icon: "◷",
                title:
                    `Interview scheduled`,
                detail:
                    interview.application
                        ?.candidate
                        ?.name
                    || "Candidate",
                time: "Scheduled"
            });

        });


    if (!activity.length) {

        container.innerHTML = `
            <div class="table-loading">
                No recent activity.
            </div>
        `;

        return;
    }


    container.innerHTML =
        activity
            .slice(0, 5)
            .map(item => `

                <div
                    class="activity">

                    <div
                        class="activity-dot">

                        ${item.icon}

                    </div>


                    <div>

                        <strong>
                            ${item.title}
                        </strong>

                        <span>
                            ${item.detail}
                        </span>

                    </div>


                    <div
                        class="activity-time">

                        ${item.time}

                    </div>

                </div>

            `)
            .join("");

}


/* =========================================================
   START
========================================================= */

document.addEventListener(
    "DOMContentLoaded",
    loadDashboard
);