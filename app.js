const state = {
  user: { name: '', program: 'B.Tech — CSE', accent: '#5DCAA5' },
  stats: [
    { label: 'Assignments due', value: 3,    sub: 'This week',        highlight: true },
    { label: 'Notes available', value: 12,   sub: 'Across 4 subjects' },
    { label: 'Submitted',       value: 8,    sub: 'Out of 11 total'   },
    { label: 'Avg score',       value: '74%',sub: 'Semester so far'   }
  ],
  assignments: [
    { title: 'Data Structures — Linked Lists', meta: 'Due: 24 Mar 2026 • Prof. Sharma', status: 'due'  },
    { title: 'DBMS — ER Diagram',              meta: 'Due: 26 Mar 2026 • Prof. Mehta',  status: 'due'  },
    { title: 'OS — Process Scheduling',        meta: 'Submitted: 19 Mar 2026',          status: 'done' },
    { title: 'CN — Subnetting Problems',       meta: 'Due was: 15 Mar 2026',            status: 'late' },
    { title: 'Maths — Integration',            meta: 'Due: 30 Mar 2026',                status: 'new'  }
  ],
  notes: [
    { title: 'Unit 3 — Trees & Graphs',    subject: 'Data Structures',   size: '2.4 MB' },
    { title: 'Normalization (1NF–3NF)',     subject: 'DBMS',              size: '1.8 MB' },
    { title: 'OSI Model — Layer by Layer', subject: 'Computer Networks',  size: '3.1 MB' },
    { title: 'Deadlocks & Semaphores',     subject: 'Operating Systems',  size: '1.2 MB' }
  ],
  schedule: [
    { time: '9:00 – 10:00',   subject: 'Data Structures',  prof: 'Prof. Sharma', room: 'Room 301', color: '#378ADD' },
    { time: '10:00 – 11:00',  subject: 'DBMS',             prof: 'Prof. Mehta',  room: 'Room 204', color: '#5DCAA5' },
    { time: '11:15 – 12:15',  subject: 'Operating Systems',prof: 'Prof. Verma',  room: 'Room 102', color: '#EF9F27' },
    { time: '2:00 – 3:00',    subject: 'Computer Networks', prof: 'Prof. Joshi',  room: 'Lab 2',    color: '#D4537E' }
  ],
  scores: [
    { sub: 'Data Structures',  score: 82, color: '#378ADD' },
    { sub: 'DBMS',             score: 76, color: '#5DCAA5' },
    { sub: 'Operating Systems',score: 68, color: '#EF9F27' },
    { sub: 'Computer Networks',score: 71, color: '#D4537E' },
    { sub: 'Mathematics',      score: 79, color: '#7F77DD' }
  ],
  attendance: {
    total: 20, present: 16, absent: 3, late: 1,
    subjects: [
      { name: 'Data Structures',  attended: 18, total: 20, color: '#378ADD' },
      { name: 'DBMS',             attended: 17, total: 20, color: '#5DCAA5' },
      { name: 'Operating Systems',attended: 15, total: 20, color: '#EF9F27' },
      { name: 'Computer Networks',attended: 14, total: 20, color: '#D4537E' },
      { name: 'Mathematics',      attended: 16, total: 20, color: '#7F77DD' }
    ],
    records: [
      { date: '22 Apr 2026', subject: 'Data Structures',  faculty: 'Prof. Sharma', status: 'present' },
      { date: '22 Apr 2026', subject: 'DBMS',             faculty: 'Prof. Mehta',  status: 'present' },
      { date: '21 Apr 2026', subject: 'Operating Systems',faculty: 'Prof. Verma',  status: 'late'    },
      { date: '21 Apr 2026', subject: 'Computer Networks',faculty: 'Prof. Joshi',  status: 'present' },
      { date: '20 Apr 2026', subject: 'Mathematics',      faculty: 'Prof. Kapoor', status: 'absent'  },
      { date: '19 Apr 2026', subject: 'Data Structures',  faculty: 'Prof. Sharma', status: 'present' },
      { date: '18 Apr 2026', subject: 'DBMS',             faculty: 'Prof. Mehta',  status: 'absent'  },
      { date: '17 Apr 2026', subject: 'Computer Networks',faculty: 'Prof. Joshi',  status: 'present' },
      { date: '16 Apr 2026', subject: 'Operating Systems',faculty: 'Prof. Verma',  status: 'absent'  }
    ]
  }
};

// ── HELPERS ──
const statusLabel = { due:'Due soon', done:'Submitted', late:'Late', new:'New' };
const iconClass   = s => ({ due:'icon-due', done:'icon-done', late:'icon-late', new:'icon-new' }[s]);

const attPctClass = pct => pct >= 80 ? 'good' : pct >= 75 ? 'ok' : 'low';

const attStatusBadge = status => {
  const map = {
    present: '<span class="badge present">Present</span>',
    absent:  '<span class="badge absent">Absent</span>',
    late:    '<span class="badge late-att">Late</span>'
  };
  return map[status] || '';
};

// ── TEMPLATES ──
const assignmentRow = a => `
  <div class="list-item">
    <div class="list-icon ${iconClass(a.status)}">
      <svg width="15" height="15" viewBox="0 0 16 16" fill="none">
        ${a.status === 'done'
          ? '<path d="M3 8l3.5 3.5L13 5" stroke="currentColor" stroke-width="1.3" stroke-linecap="round" stroke-linejoin="round"/>'
          : '<path d="M3 2h10a1 1 0 011 1v10a1 1 0 01-1 1H3a1 1 0 01-1-1V3a1 1 0 011-1z" stroke="currentColor" stroke-width="1.3"/><path d="M5 6h6M5 9h4" stroke="currentColor" stroke-width="1.3" stroke-linecap="round"/>'}
      </svg>
    </div>
    <div class="list-info"><p>${a.title}</p><span>${a.meta}</span></div>
    <span class="badge ${a.status}">${statusLabel[a.status]}</span>
  </div>`;

const noteRow = n => `
  <div class="list-item">
    <div class="list-icon icon-note">
      <svg width="15" height="15" viewBox="0 0 16 16" fill="none"><path d="M2 3h12v10H2z" stroke="currentColor" stroke-width="1.3" stroke-linejoin="round"/><path d="M5 6h6M5 9h4" stroke="currentColor" stroke-width="1.3" stroke-linecap="round"/></svg>
    </div>
    <div class="list-info"><p>${n.title}</p><span>${n.subject} • ${n.size} PDF</span></div>
    <button class="btn-sm">Download</button>
  </div>`;

const scheduleRow = s => `
  <div class="schedule-row">
    <span class="schedule-time">${s.time}</span>
    <div class="schedule-dot" style="background:${s.color}"></div>
    <div class="schedule-info"><p>${s.subject}</p><span>${s.prof}</span></div>
    <span class="schedule-room">${s.room}</span>
  </div>`;

const scoreRow = s => `
  <div class="progress-row">
    <span class="progress-label">${s.sub}</span>
    <div class="progress-track">
      <div class="progress-fill" style="width:${s.score}%;background:${s.color}"></div>
    </div>
    <span class="progress-value">${s.score}%</span>
  </div>`;

const attSubjectRow = s => {
  const pct = Math.round((s.attended / s.total) * 100);
  const cls = attPctClass(pct);
  return `
    <div class="att-progress-row">
      <span class="att-progress-label">${s.name}</span>
      <div class="att-progress-track">
        <div class="att-progress-fill" style="width:${pct}%;background:${s.color}"></div>
      </div>
      <span class="att-count">${s.attended}/${s.total}</span>
      <span class="att-pct ${cls}">${pct}%</span>
    </div>`;
};

const attRecordRow = r => `
  <tr>
    <td style="color:var(--text-muted);font-size:12px;">${r.date}</td>
    <td style="font-weight:500;">${r.subject}</td>
    <td style="color:var(--text-muted);">${r.faculty}</td>
    <td>${attStatusBadge(r.status)}</td>
  </tr>`;

// ── PAGES ──
const pages = {

  dashboard: () => ({
    title: `Good morning, ${state.user.name.split(' ')[0] || 'Student'} 👋`,
    sub: "Here's what's happening today",
    html: `
      <div class="stats-grid">
        ${state.stats.map(s => `
          <div class="stat-card ${s.highlight ? 'highlight' : ''}">
            <div class="stat-label">${s.label}</div>
            <div class="stat-value">${s.value}</div>
            <div class="stat-sub">${s.sub}</div>
          </div>`).join('')}
      </div>
      <div class="content-grid">
        <div class="card">
          <div class="card-header">
            <span class="card-title">Pending Assignments</span>
            <a class="card-link" onclick="nav('assignments')">View all</a>
          </div>
          ${state.assignments.slice(0,4).map(assignmentRow).join('')}
        </div>
        <div class="card">
          <div class="card-header">
            <span class="card-title">Recent Study Notes</span>
            <a class="card-link" onclick="nav('notes')">View all</a>
          </div>
          ${state.notes.map(noteRow).join('')}
        </div>
      </div>
      <div class="card">
        <div class="card-header">
          <span class="card-title">Today's Schedule</span>
          <a class="card-link" onclick="nav('schedule')">Full timetable</a>
        </div>
        ${state.schedule.map(scheduleRow).join('')}
      </div>`
  }),

  assignments: () => ({
    title: 'Assignments',
    sub: 'Track your pending and submitted work',
    html: `
      <div class="card">
        <div class="card-header"><span class="card-title">All Assignments</span></div>
        ${state.assignments.map(assignmentRow).join('')}
      </div>`
  }),

  notes: () => ({
    title: 'Study Notes',
    sub: 'Download materials uploaded by your faculty',
    html: `
      <div class="card">
        <div class="card-header"><span class="card-title">All Study Notes</span></div>
        ${state.notes.map(noteRow).join('')}
      </div>`
  }),

  schedule: () => ({
    title: 'Timetable',
    sub: 'Your weekly class schedule',
    html: `
      <div class="card">
        <div class="card-header"><span class="card-title">Weekly Timetable</span></div>
        ${state.schedule.map(scheduleRow).join('')}
      </div>`
  }),

  scores: () => ({
    title: 'My Scores',
    sub: 'Performance across all subjects',
    html: `
      <div class="card">
        <div class="card-header">
          <span class="card-title">My Scores</span>
          <span style="font-size:12px;color:var(--text-muted)">Semester 4 average: 74%</span>
        </div>
        ${state.scores.map(scoreRow).join('')}
      </div>`
  }),

  attendance: () => {
    const att  = state.attendance;
    const overall = Math.round((att.present / att.total) * 100);
    const ovCls   = attPctClass(overall);
    return {
      title: 'My Attendance',
      sub: 'Track your class attendance record',
      html: `
        <div class="att-summary-grid">
          <div class="att-sum-card">
            <div class="att-sum-num" style="color:var(--text-main)">${att.total}</div>
            <div class="att-sum-label">Total Classes</div>
          </div>
          <div class="att-sum-card">
            <div class="att-sum-num" style="color:#065f46">${att.present}</div>
            <div class="att-sum-label">Present</div>
          </div>
          <div class="att-sum-card">
            <div class="att-sum-num" style="color:#991b1b">${att.absent}</div>
            <div class="att-sum-label">Absent</div>
          </div>
          <div class="att-sum-card">
            <div class="att-sum-num" style="color:#92400e">${att.late}</div>
            <div class="att-sum-label">Late</div>
          </div>
        </div>

        <div class="card">
          <div class="card-header">
            <span class="card-title">Overall Attendance</span>
            <span style="font-size:12px;color:var(--text-muted)">Semester 4</span>
          </div>
          <div style="display:flex;align-items:center;gap:14px;margin-bottom:10px;">
            <span style="font-size:13px;font-weight:500;min-width:160px;">All Subjects</span>
            <div style="flex:1;height:10px;background:var(--bg);border-radius:20px;overflow:hidden;">
              <div style="width:${overall}%;height:100%;background:var(--sidebar-bg);border-radius:20px;"></div>
            </div>
            <span class="att-pct ${ovCls}">${overall}%</span>
          </div>
          <p style="font-size:12px;color:var(--text-muted);padding:10px 12px;background:#fffbeb;border-radius:8px;border:1px solid #fde68a;">
            ⚠️ Minimum required attendance is <strong>75%</strong>.
            ${overall >= 75
              ? 'You are currently <strong style="color:#065f46">above</strong> the threshold.'
              : 'You are currently <strong style="color:#991b1b">below</strong> the threshold. Please attend more classes.'}
          </p>
        </div>

        <div class="card">
          <div class="card-header">
            <span class="card-title">Subject-wise Attendance</span>
          </div>
          ${att.subjects.map(attSubjectRow).join('')}
        </div>

        <div class="card">
          <div class="card-header">
            <span class="card-title">Attendance Record</span>
            <span style="font-size:12px;color:var(--text-muted)">${att.records.length} classes</span>
          </div>
          <table class="att-table">
            <thead>
              <tr>
                <th>Date</th>
                <th>Subject</th>
                <th>Faculty</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              ${att.records.map(attRecordRow).join('')}
            </tbody>
          </table>
        </div>`
    };
  },

  submit: () => ({
    title: 'Submit Work',
    sub: 'Upload your assignment files here',
    html: `
      <div class="card" style="max-width:520px">
        <div class="card-header"><span class="card-title">Submit Assignment</span></div>
        <select class="select-field">
          <option>Select subject...</option>
          ${[...new Set(state.assignments.map(a => a.title.split('—')[0].trim()))].map(s => `<option>${s}</option>`).join('')}
        </select>
        <select class="select-field">
          <option>Select assignment...</option>
          ${state.assignments.filter(a => a.status !== 'done').map(a => `<option>${a.title}</option>`).join('')}
        </select>
        <div class="upload-area" onclick="document.getElementById('file-input').click()">
          <div class="list-icon icon-note" style="margin:0 auto">
            <svg width="15" height="15" viewBox="0 0 16 16" fill="none" stroke="currentColor" stroke-width="1.3">
              <path d="M8 2v8M5 7l3 3 3-3" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M2 12h12" stroke-linecap="round"/>
            </svg>
          </div>
          <p>Click to upload your file</p>
          <span style="font-size:11px;color:#999">PDF, DOCX up to 10 MB</span>
          <input type="file" id="file-input" style="display:none" accept=".pdf,.docx"
                 onchange="document.getElementById('fname').textContent='Selected: '+this.files[0].name">
        </div>
        <p id="fname" style="font-size:12px;color:#378ADD;margin-bottom:10px;min-height:16px;"></p>
        <button class="submit-btn" onclick="alert('Assignment submitted successfully!')">Submit Assignment</button>
      </div>`
  })
};

// ── NAVIGATION ──
function nav(section) {
  document.querySelectorAll('.nav-item').forEach(b => {
    b.classList.toggle('active', b.dataset.section === section);
  });
  const p = pages[section]();
  document.getElementById('page-title').innerText  = p.title;
  document.getElementById('page-sub').innerText    = p.sub;
  document.getElementById('dynamic-content').innerHTML = p.html;
}

// ── SETTINGS ──
function openSettings() {
  document.getElementById('input-name').value    = state.user.name;
  document.getElementById('input-program').value = state.user.program;
  document.querySelectorAll('.color-opt').forEach(o =>
    o.classList.toggle('active', o.dataset.color === state.user.accent));
  document.getElementById('settings-modal').classList.add('open');
}

function closeSettings() {
  document.getElementById('settings-modal').classList.remove('open');
}

function saveSettings() {
  state.user.name    = document.getElementById('input-name').value    || 'Student';
  state.user.program = document.getElementById('input-program').value || 'Program';
  localStorage.setItem('unishare_settings', JSON.stringify(state.user));
  updateUser();
  closeSettings();
  nav('dashboard');
}

function applyAccent(c) {
  if (!c) return;
  document.documentElement.style.setProperty('--accent', c);
  document.documentElement.style.setProperty('--accent-soft', c + '25');
}

function updateUser() {
  const initials = state.user.name.split(' ').map(n => n[0]).join('').substring(0,2).toUpperCase();
  document.getElementById('avatar-display').innerText      = initials || 'ST';
  document.getElementById('user-display-name').innerText   = state.user.name    || 'Student';
  document.getElementById('user-display-program').innerText= state.user.program || 'B.Tech — CSE';
}

function logout() {
  localStorage.removeItem('unishare_settings');
  window.location.href = 'login.html';
}

// ── INIT ──
document.addEventListener('DOMContentLoaded', () => {
  const saved = localStorage.getItem('unishare_settings');
  if (saved) {
    Object.assign(state.user, JSON.parse(saved));
    applyAccent(state.user.accent);
  }

  // Read name from login if available
  const loginName = localStorage.getItem('userName');
  if (loginName) state.user.name = loginName;

  updateUser();
  nav('dashboard');

  document.querySelectorAll('.nav-item').forEach(b =>
    b.addEventListener('click', () => nav(b.dataset.section)));

  document.querySelectorAll('.color-opt').forEach(o =>
    o.addEventListener('click', function() {
      document.querySelectorAll('.color-opt').forEach(x => x.classList.remove('active'));
      this.classList.add('active');
      state.user.accent = this.dataset.color;
      applyAccent(state.user.accent);
    }));
});