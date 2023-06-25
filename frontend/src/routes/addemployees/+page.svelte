<script>
	import { onMount, createEventDispatcher } from 'svelte';
	import axios from 'axios';
	import Navbar from '../../components/Navbar.svelte';

	axios.defaults.baseURL = 'http://localhost:8080';

	// Create an event dispatcher
	const dispatch = createEventDispatcher();

	/**
	 * @type {any[]}
	 */
	let employees = [];
	let showForm = false; // Added showForm variable

	async function getEmployees() {
	try {
		const response = await axios.get('/employee');
		if (response.status === 200) {
			employees = response.data.map((/** @type {{ id: any; }} */ employee) => ({
				...employee,
				id: employee.id,
			}));
		} else {
			throw new Error(`Failed to fetch employees. Status code: ${response.status}`);
		}
	} catch (error) {
		console.error(error);
	}
}

	/**
	 * @param {any} id
	 */
	async function deleteEmployee(id) {
		try {
			await axios.delete(`/employee/${id}`);
			// Remove the deleted employee from the employees array
			employees = employees.filter(employee => employee.id !== id);
		} catch (error) {
			console.error(error);
		}
	}

	onMount(() => {
		getEmployees();
	});

	function addEmployee() {
		// Dispatch the 'addEmployee' event
		dispatch('addEmployee');
	}

	// Listen to the 'addEmployee' event
	onMount(() => {
		// Handle the 'addEmployee' event
		document.addEventListener('addEmployee', handleAddEmployee);
	});

	/**
	 * @param {any} event
	 */
	function handleAddEmployee(event) {
		showForm = true;
	}
</script>

<Navbar />
<div class="w-2/3 mx-auto">
	<table class="min-w-full bg-white border border-gray-300">
		<thead>
			<tr>
				<th class="py-2 px-4 border-b">Name</th>
				<th class="py-2 px-4 border-b">Last Name</th>
				<th class="py-2 px-4 border-b">Phone Number</th>
				<th class="py-2 px-4 border-b">Email</th>
				<th class="py-2 px-4 border-b">Location</th>
				<th class="py-2 px-4 border-b">Actions</th>
			</tr>
		</thead>
		<tbody>
			{#each employees as employee}
			<tr>
				<td class="py-2 px-4 border-b">{employee.firstName}</td>
				<td class="py-2 px-4 border-b">{employee.lastName}</td>
				<td class="py-2 px-4 border-b">{employee.phoneNumber}</td>
				<td class="py-2 px-4 border-b">{employee.email}</td>
				<td class="py-2 px-4 border-b">{employee.locationId}</td>
				<td class="py-2 px-4 border-b">
					<button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Update</button>
					<button class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded" on:click={() => deleteEmployee(employee.id)}>Delete</button>
				</td>
			</tr>
			{/each}
		</tbody>
	</table>
</div>

<div class="w-2/3 mx-auto">
	<div class="text-center mt-4">
		<button class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded" on:click={addEmployee}>Add New Employee</button>
	</div>
</div>

<!-- New form component -->
{#if showForm}
	<div class="w-2/3 mx-auto mt-4">
		<form>
			<label for="firstName">First Name</label>
			<input type="text" id="firstName" name="firstName">

			<label for="lastName">Last Name</label>
			<input type="text" id="lastName" name="lastName">

			<label for="phoneNumber">Phone Number</label>
			<input type="text" id="phoneNumber" name="phoneNumber">

			<label for="email">Email</label>
			<input type="email" id="email" name="email">

			<label for="location">Location</label>
			<select id="location" name="location">
				<!-- Options for the dropdown -->
			</select>

			<button type="submit">Submit</button>
		</form>
	</div>
{/if}
