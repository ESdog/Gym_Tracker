package com.example.gymtracker.ui.exercise

import com.example.gymtracker.ui.components.SetInputRow
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.gymtracker.ui.theme.GymTrackerTheme

@Composable
fun ExerciseEntryScreen(
    modifier: Modifier = Modifier
) {
    var exerciseName by rememberSaveable {
        mutableStateOf("")
    }

    var warmupWeight by rememberSaveable {
        mutableStateOf("")
    }

    var warmupReps by rememberSaveable {
        mutableStateOf("")
    }

    // Each pair contains a working set's weight and reps.
    var workingSets by rememberSaveable(
        stateSaver = listSaver<List<String>, String>(
            save = { it },
            restore = { it.toList() }
        )
    ) {
        mutableStateOf(listOf("10", "9", "", ""))
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Gym Tracker",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        item {
            OutlinedTextField(
                value = exerciseName,
                onValueChange = { exerciseName = it },
                label = { Text("Exercise name") },
                placeholder = { Text("e.g. Bench press") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Text(
                text = "Warm-up",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            SetInputRow(
                weight = warmupWeight,
                reps = warmupReps,
                onWeightChange = { warmupWeight = it },
                onRepsChange = { warmupReps = it }
            )
        }

        item {
            HorizontalDivider()
        }

        item {
            Text(
                text = "Working sets",
                style = MaterialTheme.typography.titleLarge
            )
        }

        itemsIndexed(workingSets.chunked(2)) { index, values ->
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Set ${index + 1}",
                    style = MaterialTheme.typography.titleMedium
                )

                SetInputRow(
                    weight = values[0],
                    reps = values[1],
                    onWeightChange = { newWeight ->
                        workingSets = workingSets.toMutableList().apply {
                            this[index * 2] = newWeight
                        }
                    },
                    onRepsChange = { newReps ->
                        workingSets = workingSets.toMutableList().apply {
                            this[index * 2 + 1] = newReps
                        }
                    }
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        workingSets = workingSets + listOf("", "")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add set")
                }

                OutlinedButton(
                    onClick = {
                        workingSets = workingSets.dropLast(2)
                    },
                    enabled = workingSets.size > 2,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Remove last")
                }
            }
        }

        item {
            Text(
                text = "Entry screen only — permanent saving comes next.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}


