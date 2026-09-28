// ================================
// POST JOB MODAL
// ================================

const jobModal = document.getElementById("jobModal");
const openJobButton = document.querySelector(".add-job-btn");
const closeJobButton = document.getElementById("closeJobModal");
const cancelJobButton = document.getElementById("cancelJob");
const jobForm = document.getElementById("jobForm");
const jobFormMessage = document.getElementById("jobFormMessage");

function openJobModal() {

    jobModal.classList.add("show");

    document.getElementById("jobTitle").focus();
}

function closeJobModal() {

    jobModal.classList.remove("show");

    jobForm.reset();

    jobFormMessage.textContent = "";
    jobFormMessage.className = "form-message";
}

openJobButton.addEventListener(
    "click",
    openJobModal
);

closeJobButton.addEventListener(
    "click",
    closeJobModal
);

cancelJobButton.addEventListener(
    "click",
    closeJobModal
);

jobModal.addEventListener(
    "click",
    (event) => {

        if (event.target === jobModal) {
            closeJobModal();
        }

    }
);


jobForm.addEventListener(
    "submit",
    async (event) => {

        event.preventDefault();

        jobFormMessage.textContent = "Creating job...";
        jobFormMessage.className = "form-message";

        const job = {
            title: document.getElementById("jobTitle").value.trim(),
            description: document.getElementById("jobDescription").value.trim(),
            location: document.getElementById("jobLocation").value.trim(),
            experience: Number(
                document.getElementById("jobExperience").value
            ),
            status: "OPEN"
        };

        try {

            const response = await fetch(
                "/api/jobs",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(job)
                }
            );

            if (!response.ok) {
                throw new Error(
                    `Failed to create job (${response.status})`
                );
            }

            jobFormMessage.textContent =
                "Job created successfully.";

            jobFormMessage.className =
                "form-message success";

            await loadDashboard();

            setTimeout(
                closeJobModal,
                900
            );

        } catch (error) {

            console.error(error);

            jobFormMessage.textContent =
                "Unable to create job. Please try again.";

            jobFormMessage.className =
                "form-message error";
        }
    }
);