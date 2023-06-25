<script>
	import Navbar from "../../components/Navbar.svelte";
	import { Button } from "flowbite-svelte";
</script>

<Navbar />
<div class="bg-gray-200 min-h-screen">
	<div class="container mx-auto py-8">
		<div class="bg-white rounded shadow-md px-8 py-6 mb-4">
			<h2 class="text-2xl font-bold mb-4">Filter By:</h2>
			<div class="flex flex-wrap -mx-2">
				<div class="w-full md:w-1/2 px-2 mb-4">
					<label
						class="block uppercase tracking-wide text-gray-700 font-bold mb-2"
						for="location-select"
					>
						Location
					</label>
					<div class="relative">
						<select
							id="location-select"
							name="location"
							class="appearance-none w-full px-3 py-2 rounded leading-tight focus:outline-none focus:shadow-outline bg-gray-200"
						>
							<option value="">Select a location</option>
							<option value="location-1">Location 1</option>
							<option value="location-2">Location 2</option>
							<option value="location-3">Location 3</option>
						</select>
						<div
							class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700"
						>
						</div>
					</div>
				</div>
				<div class="w-full md:w-1/2 px-2 mb-4">
					<label
						class="block uppercase tracking-wide text-gray-700 font-bold mb-2"
						for="gate-select"
					>
						Gate
					</label>
					<div class="relative">
						<select
							id="gate-select"
							name="gate"
							class="appearance-none w-full px-3 py-2 rounded leading-tight focus:outline-none focus:shadow-outline bg-gray-200"
						>
							<option value="">Select a gate</option>
							<option value="gate-1">Gate 1</option>
							<option value="gate-2">Gate 2</option>
							<option value="gate-3">Gate 3</option>
						</select>
						<div
							class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700"
						>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="text-center mt-4">
			<Button color="blue" style="margin-right: 0.5rem">Generate PDF</Button>
		</div>
	</div>
</div>
